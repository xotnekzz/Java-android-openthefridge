package com.example.kimseolki.refrigerator_acin;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
//이거 사용

/**
 * Created by kimseolki on 2017-05-02.
 */

        public class SettingsFragment extends Fragment {

            private static final String TAG = "SettingFragment";
            EditText ettime;
            Spinner spdate;
            TextView testdb;
            int year, month, day, hour, minute;
            ToggleButton pushonoff;
            SharedPreferences preferences;
            SharedPreferences.Editor editor;



            //Sharedpreferences 해보자

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                final View rootview = inflater.inflate(R.layout.activity_setting,container,false);
                GregorianCalendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day= calendar.get(Calendar.DAY_OF_MONTH);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                ettime = (EditText) rootview.findViewById(R.id.ettime);
                ettime.setInputType(InputType.TYPE_NULL);
                spdate = (Spinner) rootview.findViewById(R.id.spdate);
                pushonoff = (ToggleButton) rootview.findViewById(R.id.pushonoff);


                SharedPreferences preferences = this.getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);


                String gettime = preferences.getString("time","");
                ettime.setText(gettime);

                boolean push = preferences.getBoolean("push", false);  //default is true
                pushonoff.setChecked(push);


                ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.arexdate, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spdate.setAdapter(adapter);

                String getdate = preferences.getString("date","1일전");
                if(getdate.equals("1일전")) {
                    spdate.setSelection(0);
                }
                else if (getdate.equals("2일전")){
                    spdate.setSelection(1);
                }
                else if (getdate.equals("3일전")){
                    spdate.setSelection(2);
                }
                else if (getdate.equals("5일전")){
                    spdate.setSelection(3);
                }
                else if (getdate.equals("7일전")){
                    spdate.setSelection(4);
                }
                else if (getdate.equals("10일전")){
                    spdate.setSelection(5);
                }

                spdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                rootview.findViewById(R.id.ettime)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new TimePickerDialog(getActivity(), timeSetListener, hour, minute, false).show();
                            }
                        });

                return rootview;
            }




    @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setHasOptionsMenu(true);
            }

            @Override
            public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                // TODO Auto-generated method stub
                super.onCreateOptionsMenu(menu, inflater);
                inflater.inflate(R.menu.action_bar_setting, menu);
            }




            private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String aMpM = "AM";
                    if(hourOfDay>11){
                        aMpM = "PM";
                    }
                    int currentHour;
                    if(hourOfDay >11) {
                        currentHour = hourOfDay - 12;
                    }
                    else{
                        currentHour = hourOfDay;
                    }
                    ettime.setText("");
                    ettime.setText(ettime.getText() + String.valueOf(hourOfDay) + "시"
                            + String.valueOf(minute)+ "분\n");
                }
            };

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.atsetting:
                        SharedPreferences preferences = this.getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor= preferences.edit();
                        String time = ettime.getText().toString();
                        String date = spdate.getSelectedItem().toString();
                        editor.putString("time", time);
                        editor.putString("date", date);
                        if((pushonoff.isChecked()))
                        {
                            editor.putBoolean("push", true); // value to store
                        }
                        else
                        {
                            editor.putBoolean("push", false); // value to store
                        }
                        editor.commit();
                        Toast.makeText(getActivity(), "설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();


                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }





        }

