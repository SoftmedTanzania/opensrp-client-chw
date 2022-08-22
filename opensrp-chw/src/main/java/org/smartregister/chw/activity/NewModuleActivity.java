package org.smartregister.chw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vijay.jsonwizard.activities.JsonFormActivity;
import com.vijay.jsonwizard.activities.JsonWizardFormActivity;
import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;
import com.vijay.jsonwizard.factory.FileSourceFactoryHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.smartregister.chw.R;
import org.smartregister.chw.core.activity.CoreMalariaRegisterActivity;
import org.smartregister.chw.util.Constants;

public class NewModuleActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_GET_JSON =1234;
    private static final String TAG = NewModuleActivity.class.getCanonicalName();

    public static void NewModuleActivity(AncMemberProfileActivity ancMemberProfileActivity, String baseEntityId, Object o) {

    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view);
        findViewById(R.id.btn_module).setOnClickListener((View.OnClickListener) this);

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String jsonString = data.getStringExtra("json");
            Log.i(getClass().getName(), "Result json String !!!! " + jsonString);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void startForm(int jsonFormActivityRequestCode, String formName, String entityId, boolean translate) throws Exception {

        final String STEP1 = "step1";
        final String FIELDS = "fields";
        final String KEY = "key";
        final String ZEIR_ID = "ZEIR_ID";
        final String VALUE = "value";

        String currentLocationId = "Kenya";

        JSONObject jsonForm = FileSourceFactoryHelper.getFileSource("").getFormFromFile(getApplicationContext(), formName);
        if (jsonForm != null) {
            jsonForm.getJSONObject("metadata").put("encounter_location", currentLocationId);

            switch (formName) {
                case "new_module":{
                    Intent intent = new Intent(this, JsonFormActivity.class);
                    intent.putExtra("json", jsonForm.toString());

                    Form form = new Form();
                    form.setName("New Module");
                    form.setWizard(true);
                    form.setSaveLabel("Save");

                    intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, form);

                    startActivityForResult(intent, jsonFormActivityRequestCode);
                    break;
                }

                default: {

                    if (entityId == null) {
                        entityId = "ABC" + Math.random();
                    }


                    // Inject zeir id into the form
                    JSONObject stepOne = jsonForm.getJSONObject(STEP1);
                    JSONArray jsonArray = stepOne.getJSONArray(FIELDS);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString(KEY)
                                .equalsIgnoreCase(ZEIR_ID)) {
                            jsonObject.remove(VALUE);
                            jsonObject.put(VALUE, entityId);
                            continue;
                        }
                    }

                    Intent intent = new Intent(this, JsonFormActivity.class);
                    intent.putExtra("json", jsonForm.toString());
                    intent.putExtra(JsonFormConstants.PERFORM_FORM_TRANSLATION, translate);
                    Log.d(getClass().getName(), "form is " + jsonForm.toString());
                    startActivityForResult(intent, jsonFormActivityRequestCode);
                    break;
                }
            }

        }

    }
   @Override
   public void onClick(View view) {
       int id = view.getId();
       if (id == R.id.btn_module) {
           try {
               startForm(REQUEST_CODE_GET_JSON, "new_module", null, false);
           } catch (Exception e) {
               e.printStackTrace();
           }

       }

   }
}

