package com.guideteee.myapplication;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mapbox.mapboxsdk.maps.MapFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogIn extends Fragment {
    EditText AdvisorNamelo, Password;
    Context conttextl;
    MainActivity activity;
    DbHandler db;
    CardView loginlo;
    MapFragment mapFragment;
    public LogIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);





       activity = (MainActivity) getActivity();

        db = DbObject.getDb();//new DbHandler(getActivity());
        conttextl=activity.context;

        AdvisorNamelo=(EditText) view.findViewById(R.id.advisornamelogin);
        Password=(EditText) view.findViewById(R.id.passwordlog);
        loginlo =(CardView) view.findViewById(R.id.loginlogin);

    loginlo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View vk) {
            checklogin(vk);
        }
    });
    //db=new DbHandler(MainActivity.this);
//inserting dummy users

      // mapFragment =MainActivity.mapFragment;
               //(MapFragment) getFragmentManager().findFragmentByTag("com.mapbox.map");

        return view;

    }
    public int checkUser(User user)
    {
        return db.checkUser(user);
    }



    public void checklogin(View vk)
    {



        String name=AdvisorNamelo.getText().toString();
        String password=Password.getText().toString();



        if(name.isEmpty() || password.isEmpty())
        {
            Message.message(conttextl,"Enter Both Name and Password");
        }
        else
        {

            int id= checkUser(new User(name,password));
            if(id==-1)
            {
                Toast.makeText(getActivity(),"User Does Not Exist",Toast.LENGTH_SHORT).show();
                AdvisorNamelo.setText("");
                Password.setText("");
            }
            else
            {
               //Toast.makeText(getActivity(),"User Exist "+name,Toast.LENGTH_SHORT).show();
                AdvisorNamelo.setText("");
                Password.setText("");


                String phone= db.phonenum(id);

                Long idd=db.addlog(new User(name,password,phone));
                Toast.makeText(getActivity(),phone+name,Toast.LENGTH_SHORT).show();
                db.close();
                //Intent countentintent =  new Intent(getActivity(), MainActivity.class);
               // startActivity(countentintent);
          //  getFragmentManager().beginTransaction().replace(R.id.content_frame, mapFragment).commit();
  if(idd==-1)
  {
      Toast.makeText(getActivity(),"cant add to log table2 "+name,Toast.LENGTH_LONG).show();
  }

            }

        }
    }




}



