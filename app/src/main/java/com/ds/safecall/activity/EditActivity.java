package com.ds.safecall.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ds.safecall.R;
import com.ds.safecall.bean.People;
import com.ds.safecall.util.ManagerUtil;
import com.ds.safecall.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends BaseActivity {

    @BindView(R.id.edit_home_layout)
    LinearLayout homeLayout;

    @BindView(R.id.back_btn)
    ImageView backBtn;

    @BindView(R.id.people_layout_1)
    LinearLayout peopleLayout1;

    @BindView(R.id.people_layout_2)
    LinearLayout peopleLayout2;

    @BindView(R.id.people_layout_3)
    LinearLayout peopleLayout3;

    @BindView(R.id.add_next_people)
    TextView addPeopleBtn;

    @BindView(R.id.user1_name)
    EditText username1;
    @BindView(R.id.user1_email)
    EditText useremail1;
    @BindView(R.id.user1_phone)
    EditText userphone1;

    @BindView(R.id.user2_name)
    EditText username2;
    @BindView(R.id.user2_email)
    EditText useremail2;
    @BindView(R.id.user2_phone)
    EditText userphone2;

    @BindView(R.id.user3_name)
    EditText username3;
    @BindView(R.id.user3_email)
    EditText useremail3;
    @BindView(R.id.user3_phone)
    EditText userphone3;

    @BindView(R.id.save_btn)
    TextView saveBtn;

    @Override
    public void initView() {
        setContentView(R.layout.edit_activity);
        ButterKnife.bind(this);
        fixedStatusBar(homeLayout);
    }

    @Override
    public void initEvent() {
        backBtn.setOnClickListener(savePeopleClick);
        addPeopleBtn.setOnClickListener(addNextPeopleClick);
        saveBtn.setOnClickListener(savePeopleClick);
    }

    @Override
    public void initData() {
        List<People> peoples = SharedPreferencesUtils.getPeople(EditActivity.this);
        initPeople(peoples);
    }

    private void initPeople(List<People> peopleList) {
        int size = peopleList == null ? 0 : peopleList.size();
        initPeopleLayout(size);
        clearPeople(size);
        if(size > 0) {
            username1.setText(peopleList.get(0).getName());
            useremail1.setText(peopleList.get(0).getEmail());
            userphone1.setText(peopleList.get(0).getPhone());
        }

        if(size > 1) {
            username2.setText(peopleList.get(1).getName());
            useremail2.setText(peopleList.get(1).getEmail());
            userphone2.setText(peopleList.get(1).getPhone());
        }

        if(size > 2) {
            username3.setText(peopleList.get(2).getName());
            useremail3.setText(peopleList.get(2).getEmail());
            userphone3.setText(peopleList.get(2).getPhone());
        }
    }

    private void clearPeople(int size) {
        if(size <= 0) {
            username1.setText("");
            useremail1.setText("");
            userphone1.setText("");
        }

        if(size <= 1) {
            username2.setText("");
            useremail2.setText("");
            userphone2.setText("");
        }

        if(size <= 2) {
            username3.setText("");
            useremail3.setText("");
            userphone3.setText("");
        }
    }

    private boolean checkInput() {
        if(peopleLayout1.getVisibility() == View.VISIBLE) {
            String name1 = username1.getText().toString();
            String email1 = useremail1.getText().toString();
            String phone1 = userphone1.getText().toString();
            if(TextUtils.isEmpty(name1)
                        && TextUtils.isEmpty(email1)
                        && TextUtils.isEmpty(phone1)) {

            } else if(TextUtils.isEmpty(name1)) {
                username1.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(TextUtils.isEmpty(email1)) {
                useremail1.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(!ManagerUtil.isEmail(email1)) {
                useremail1.requestFocus();
                Toast.makeText(EditActivity.this,
                        String.format(getResources().getString(R.string.tip_for_email)
                        ,email1),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(TextUtils.isEmpty(phone1)) {
                userphone1.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(!ManagerUtil.isMobile(phone1)) {
                userphone1.requestFocus();
                Toast.makeText(EditActivity.this,
                        String.format(getResources().getString(R.string.tip_for_phone)
                                ,phone1),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }

        if(peopleLayout2.getVisibility() == View.VISIBLE) {
            String name2 = username2.getText().toString();
            String email2 = useremail2.getText().toString();
            String phone2 = userphone2.getText().toString();
            if(TextUtils.isEmpty(name2)
                        && TextUtils.isEmpty(email2)
                        && TextUtils.isEmpty(phone2)) {

            } else if(TextUtils.isEmpty(name2)) {
                username2.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(TextUtils.isEmpty(email2)) {
                useremail2.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(!ManagerUtil.isEmail(email2)) {
                useremail2.requestFocus();
                Toast.makeText(EditActivity.this,
                        String.format(getResources().getString(R.string.tip_for_email)
                                ,email2),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(TextUtils.isEmpty(phone2)) {
                userphone2.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(!ManagerUtil.isMobile(phone2)) {
                userphone2.requestFocus();
                Toast.makeText(EditActivity.this,
                        String.format(getResources().getString(R.string.tip_for_phone)
                                ,phone2),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }

        if(peopleLayout3.getVisibility() == View.VISIBLE) {
            String name3 = username3.getText().toString();
            String email3 = useremail3.getText().toString();
            String phone3 = userphone3.getText().toString();
            if(TextUtils.isEmpty(name3)
                        && TextUtils.isEmpty(email3)
                        && TextUtils.isEmpty(phone3)) {

            } else if(TextUtils.isEmpty(name3)) {
                username3.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(TextUtils.isEmpty(email3)) {
                useremail3.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(!ManagerUtil.isEmail(email3)) {
                useremail3.requestFocus();
                Toast.makeText(EditActivity.this,
                        String.format(getResources().getString(R.string.tip_for_email)
                                ,email3),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(TextUtils.isEmpty(phone3)) {
                userphone3.requestFocus();
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_empty),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            } else if(!ManagerUtil.isMobile(phone3)) {
                userphone2.requestFocus();
                Toast.makeText(EditActivity.this,
                        String.format(getResources().getString(R.string.tip_for_phone)
                                ,phone3),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }

        return true;
    }

    private List<People> getInputPeople() {
        List<People> people = new ArrayList<>();
        if(peopleLayout1.getVisibility() == View.VISIBLE) {
            String name1 = username1.getText().toString();
            String email1 = useremail1.getText().toString();
            String phone1 = userphone1.getText().toString();
            if(!TextUtils.isEmpty(name1)
                    && !TextUtils.isEmpty(email1)
                    && !TextUtils.isEmpty(phone1)) {
                People people1 = new People();
                people1.setName(name1);
                people1.setEmail(email1);
                people1.setPhone(phone1);
                people.add(people1);
            }
        }

        if(peopleLayout2.getVisibility() == View.VISIBLE) {
            String name2 = username2.getText().toString();
            String email2 = useremail2.getText().toString();
            String phone2 = userphone2.getText().toString();
            if(!TextUtils.isEmpty(name2)
                    && !TextUtils.isEmpty(email2)
                    && !TextUtils.isEmpty(phone2)) {
                People people2 = new People();
                people2.setName(name2);
                people2.setEmail(email2);
                people2.setPhone(phone2);
                people.add(people2);
            }
        }

        if(peopleLayout3.getVisibility() == View.VISIBLE) {
            String name3 = username3.getText().toString();
            String email3 = useremail3.getText().toString();
            String phone3 = userphone3.getText().toString();
            if(!TextUtils.isEmpty(name3)
                    && !TextUtils.isEmpty(email3)
                    && !TextUtils.isEmpty(phone3)) {
                People people3 = new People();
                people3.setName(name3);
                people3.setEmail(email3);
                people3.setPhone(phone3);
                people.add(people3);
            }
        }
        return people;
    }
    private void initPeopleLayout(int size) {
        peopleLayout1.setVisibility(View.VISIBLE);
        peopleLayout2.setVisibility(size==0|| size ==1 ? View.GONE :View.VISIBLE);
        peopleLayout3.setVisibility(size == 3 ? View.VISIBLE :View.GONE);
    }

    View.OnClickListener savePeopleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            closeAndSave();

        }
    };

    View.OnClickListener addNextPeopleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(peopleLayout1.getVisibility() == View.GONE) {
                peopleLayout1.setVisibility(View.VISIBLE);
                return;
            }
            if(peopleLayout2.getVisibility() == View.GONE) {
                peopleLayout2.setVisibility(View.VISIBLE);
                return;
            }
            if(peopleLayout3.getVisibility() == View.GONE) {
                peopleLayout3.setVisibility(View.VISIBLE);
            }
            addPeopleBtn.setEnabled(false);
            addPeopleBtn.setVisibility(View.GONE);
        }
    };

    /*View.OnClickListener finishClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditActivity.this.finish();
        }
    };*/

    private void closeAndSave() {
        if(checkInput()) {
            List<People> people = getInputPeople();

            SharedPreferencesUtils.save(EditActivity.this, people);

            if(people == null || people.size() == 0) {
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_one),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                if(!ManagerUtil.HOME_ACTIVITY_START) {
                    gotoActivity(HomeActivity.class);
                    return;
                }
                Toast.makeText(EditActivity.this,
                        getResources().getString(R.string.tip_for_success),
                        Toast.LENGTH_SHORT)
                        .show();
                EditActivity.this.finish();
            }

        }
    }

    private void gotoActivity(final Class cls) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(EditActivity.this, cls));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            }
        }, 500);
    }
}
