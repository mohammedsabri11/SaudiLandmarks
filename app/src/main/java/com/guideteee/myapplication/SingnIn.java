package com.guideteee.myapplication;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingnIn extends Fragment{
EditText AdvisorName, Password,Passwordconfirm,Phonenum;
Context conttext;
CardView CreateAdvisorAcount;
    DbHandler db;



        // Required empty public constructor


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singn_in, container, false);


        AdvisorName=(EditText) view.findViewById(R.id.advisorname_singn_in);
        Password=(EditText) view.findViewById(R.id.password1_singn_in);
        Passwordconfirm=(EditText)
                view.findViewById(R.id.password2_singn_in);
        Phonenum=(EditText) view.findViewById(R.id.phoneNum);
        CreateAdvisorAcount = (CardView) view.findViewById(R.id.createAcount);
        CreateAdvisorAcount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View ve) {
                // Switching to Register screen
                AddUser(ve);
               // Intent i = new Intent(conttext, MainActivity.class);
               // startActivity(i);
            }  });



        this.db = DbObject.getDb();//new DbHandler(getActivity());//getdbobject.getDb();

        return view;



    }

    public void AddUser(View view)
    {
        String advisorname =AdvisorName.getText().toString();
        String pasword1 = Password.getText().toString();
        String password2 =  Passwordconfirm.getText().toString();
        String phonenum_c=  Phonenum.getText().toString();
        if(advisorname.isEmpty() || pasword1.isEmpty()|| password2.isEmpty()|| phonenum_c.isEmpty())
        {
            Message.message(getActivity(),"Enter all information dont let  blanck");
        }
        else
        {
            if (!pasword1.matches(password2))
            {
                Message.message(getActivity(),"password dont matches");
            }else {


                long id = db.addUser(new User( advisorname, pasword1,phonenum_c));
                if (id <= 0) {
                    Message.message(getActivity(), "Insertion Unsuccessful");
                    AdvisorName.setText("");
                    Password.setText("");
                    Phonenum.setText("");
                    Passwordconfirm.setText("");
                } else {
                    Message.message(getActivity(), "Insertion Successful");
                    AdvisorName.setText("");
                    Password.setText("");
                    Phonenum.setText("");
                    Passwordconfirm.setText("");
                    db.close();
                  //  FragmentTransaction t = this.getFragmentManager().beginTransaction();
                   // Fragment mFrag = new LogIn();
                   // t.replace(R.id.frag, mFrag);
                   // t.commit();
                }
            }
        }
    }


}


