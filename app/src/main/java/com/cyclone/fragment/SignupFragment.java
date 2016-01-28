package com.cyclone.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyclone.EmptyActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.loopback.repository.CreatUserRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

/**
 * Created by gilang on 22/10/2015.
 */
public class SignupFragment extends Fragment {

	private Button btnSignup;
	private TextView txtToc;
	private Spinner spinnerGender;
	private String[] spinnerGenderItems = {"Male", "Female", "Gender"};

	ProgressDialog progressDialog;
	EditText editTextUsername, editTextFullName, editTextPassword, editTextEmail;
	TextInputLayout labelUser, labelName, labelPassword, labelEmail;
	int cntLogin = 0;

	public SignupFragment(){}

	public static SignupFragment newInstance(){
		SignupFragment fragment = new SignupFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_signup, parent, false);

		btnSignup = (Button) v.findViewById(R.id.btn_signup);
		spinnerGender = (Spinner) v.findViewById(R.id.spinner_gender);
		txtToc = (TextView) v.findViewById(R.id.txt_toc);
		labelUser = (TextInputLayout) v.findViewById(R.id.input_layout_user);
		labelEmail = (TextInputLayout) v.findViewById(R.id.input_layout_email);
		labelPassword = (TextInputLayout) v.findViewById(R.id.input_layout_password);
		labelName  = (TextInputLayout) v.findViewById(R.id.input_layout_name);
		editTextUsername = (EditText) v.findViewById(R.id.form_user);
		editTextEmail = (EditText) v.findViewById(R.id.form_email);
		editTextFullName = (EditText) v.findViewById(R.id.form_full_name);
		editTextPassword = (EditText) v.findViewById(R.id.form_password);

		btnSignup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();*/
				progressDialog = new ProgressDialog(getActivity(),R.style.transparentProgesDialog);
				progressDialog.setCancelable(false);
				progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
				progressDialog.show();
				if(isFormValid())
					register();

			}
		});


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.text, spinnerGenderItems){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				View v = super.getView(position, convertView, parent);
				if (position == getCount()) {
					((TextView)v.findViewById(R.id.text)).setText(getItem(getCount()));
				}

				return v;
			}

			@Override
			public int getCount() {
				return super.getCount() - 1;
			}
		};
		spinnerGender.setAdapter(adapter);
		spinnerGender.setSelection(adapter.getCount());

		String tocString = txtToc.getText().toString();
		final int startIdx = tocString.indexOf("Term");
		int endIdx = tocString.indexOf("Conditions") + 10;
		SpannableString ss = new SpannableString(txtToc.getText());
		ClickableSpan span = new ClickableSpan() {
			@Override
			public void onClick(View widget) {
				Intent i = new Intent(getContext(), EmptyActivity.class);
				i.putExtra("fragmentType", EmptyActivity.FRAGMENT_TOC);
				getActivity().startActivity(i);
			}
		};
		ss.setSpan(span, startIdx, endIdx, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		txtToc.setText(ss);
		txtToc.setMovementMethod(LinkMovementMethod.getInstance());
		txtToc.setHighlightColor(Color.BLUE);

		return v;
	}

	private void hidekeyboard(){
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	private void register(){
		hidekeyboard();

		String male = "true";
		String email, fullname, user, password;

		if(editTextUsername.getText().length() < 1 || editTextUsername.getText().equals(" ")){
			progressDialog.dismiss();
//			Snackbar.make(getView(), "Username is Empty", Snackbar.LENGTH_SHORT).show();
			return;
		}
		if(editTextEmail.getText().length() < 1 || editTextEmail.getText().equals(" ")){
			progressDialog.dismiss();
//			Snackbar.make(getView(),"Email is Empty", Snackbar.LENGTH_SHORT).show();
			return;
		}
		if(editTextPassword.getText().length() < 1 || editTextPassword.getText().equals(" ")){
			progressDialog.dismiss();
//			Snackbar.make(getView(),"Password is Empty", Snackbar.LENGTH_SHORT).show();
			return;
		}

		email = editTextEmail.getText().toString();
		fullname = editTextFullName.getText().toString();
		user = editTextUsername.getText().toString();
		password = editTextPassword.getText().toString();

		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		CreatUserRepository creatUserRepository = restAdapter.createRepository(CreatUserRepository.class);

		creatUserRepository.createContract();
		creatUserRepository.creat(user, email, "true", password, new Adapter.Callback() {
			@Override
			public void onSuccess(String response) {
				progressDialog.dismiss();
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();
			}

			@Override
			public void onError(Throwable t) {
				if (cntLogin == 0) {
					ServerUrl.API_URL = ServerUrl.API_URL_local;
					cntLogin++;
					Snackbar.make(getView(), "Connecting to local", Snackbar.LENGTH_SHORT).show();
					register();
				} else {
					progressDialog.dismiss();
					Snackbar.make(getView(), "Error SignUp", Snackbar.LENGTH_LONG).show();
				}
			}
		});

	}

	private boolean isFormValid(){
		if(editTextUsername.getText().toString().equals("aaa")){
			labelUser.setError("User already exist");
			editTextUsername.requestFocus();
			progressDialog.dismiss();
			return false;
		}else{
			labelUser.setErrorEnabled(false);
		}
		if(editTextUsername.getText().toString().equals("")){
			labelUser.setError("Username is empty");
			editTextUsername.requestFocus();
			progressDialog.dismiss();
			return false;
		}else{
			labelUser.setErrorEnabled(false);
		}
		if(editTextFullName.getText().toString().equals("")){
			labelName.setError("Name is empty");
			editTextFullName.requestFocus();
			progressDialog.dismiss();
			return false;
		}else{
			labelName.setErrorEnabled(false);
		}
		if(editTextEmail.getText().toString().equals("")){
			labelEmail.setError("Email is empty");
			editTextEmail.requestFocus();
			progressDialog.dismiss();
			return false;
		}else{
			labelEmail.setErrorEnabled(false);
		}
		if(editTextPassword.getText().toString().equals("")){
			labelPassword.setError("Password is empty");
			editTextPassword.requestFocus();
			progressDialog.dismiss();
			return false;
		}else{
			labelPassword.setErrorEnabled(false);
		}
		return true;
	}
}
