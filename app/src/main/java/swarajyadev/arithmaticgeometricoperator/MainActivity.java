package swarajyadev.arithmaticgeometricoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView selecteditem,show;
    EditText exp,value,base,valx,fofx,startval,endval,fx;
    Button submit,reset;
    LinearLayout log,tangent,curve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selecteditem = findViewById(R.id.selectitem);
        show = findViewById(R.id.show);
        exp = findViewById(R.id.exp);
        submit = findViewById(R.id.submit);
        reset = findViewById(R.id.reset);
        //Log
        log = findViewById(R.id.log);
        value = findViewById(R.id.value);
        base = findViewById(R.id.base);
        //tangent
        tangent = findViewById(R.id.tangent);
        valx = findViewById(R.id.valx);
        fofx = findViewById(R.id.fofx);
        //curve
        curve = findViewById(R.id.curve);
        startval = findViewById(R.id.startval);
        endval = findViewById(R.id.endval);
        fx = findViewById(R.id.fx);


        String[] arraySpinner = new String[] {
                "Simplify", "Factor", "Derivative", "Integration", "Find 0's", "Sine", "Cosine", "Tan", "Inverse Sine",
                "Inverse Cosine", "Inverse Tan", "Absolute Value", "Logarithm", "Find Tangent", "Area Under Curve"
        };
        final Spinner s = (Spinner) findViewById(R.id.mnspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mn=s.getSelectedItem().toString();

                if (mn.equals("Logarithm")){
                    exp.setVisibility(View.GONE);
                    tangent.setVisibility(View.GONE);
                    curve.setVisibility(View.GONE);
                    log.setVisibility(View.VISIBLE);
                    selecteditem.setText("Logarithm");
                }
                else if (mn.equals("Find Tangent")){
                    exp.setVisibility(View.GONE);
                    log.setVisibility(View.GONE);
                    curve.setVisibility(View.GONE);
                    tangent.setVisibility(View.VISIBLE);
                    selecteditem.setText("Find Tangent");
                }
                else if (mn.equals("Area Under Curve")){
                    exp.setVisibility(View.GONE);
                    log.setVisibility(View.GONE);
                    tangent.setVisibility(View.GONE);
                    curve.setVisibility(View.VISIBLE);
                    selecteditem.setText("Area Under Curve");
                }
                else{
                    selecteditem.setText(mn);
                    log.setVisibility(View.GONE);
                    tangent.setVisibility(View.GONE);
                    curve.setVisibility(View.GONE);
                    exp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.animate));

                //For Log
                if(selecteditem.getText().toString().equals("Logarithm")){
                    if (value.getText().toString().equals("")&&base.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Expressions cannot be empty !", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String val=value.getText().toString();
                        String bs = base.getText().toString();
                        String explog = val+"|"+bs;
                        exp.setText(explog);
                    }
                }
                else if (selecteditem.getText().toString().equals("Find Tangent")){
                    if (valx.getText().toString().equals("")&&fofx.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Values or expressions cannot be empty !", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String vx = valx.getText().toString();
                        String fx = fofx.getText().toString();
                        String exptan = vx+"|"+fx;
                        exp.setText(exptan);
                    }

                }
                else if (selecteditem.getText().toString().equals("Area Under Curve")){
                    String sval = startval.getText().toString();
                    String eval = endval.getText().toString();
                    String fox = fx.getText().toString();

                    if (sval.equals("")|eval.equals("")|fox.equals("")){
                        Toast.makeText(MainActivity.this, "Values or Expressions cannot be empty !", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String expcurve = sval+":"+eval+"|"+fox;
                        exp.setText(expcurve);
                    }
                }



                int selec = s.getSelectedItemPosition();
                String Exp = exp.getText().toString();
                //Toast.makeText(MainActivity.this, Exp, Toast.LENGTH_SHORT).show();
                if (Exp.equals("")){
                    Toast.makeText(MainActivity.this, "Expression cannot be empty !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    submit.setText("Processing...");
                    switch (selec){
                        case 0: String simplify = "https://newton.now.sh/simplify/"+Exp;
                                Json(simplify);
                                break;
                        case 1:String factor = "https://newton.now.sh/factor/"+Exp;
                                Json(factor);
                                break;
                        case 2:String derivate = "https://newton.now.sh/derive/"+Exp;
                                Json(derivate);
                                break;
                        case 3:String integrate = "https://newton.now.sh/integrate/"+Exp;
                            Json(integrate);
                            break;
                        case 4:String findo = "https://newton.now.sh/zeroes/"+Exp;
                            Json(findo);
                            break;
                        case 5:double sinmn=Double.parseDouble(Exp);
                            double sin =sinmn*0.0174532925;
                            String sine = "https://newton.now.sh/sin/"+sin;
                            Json(sine);
                            break;
                        case 6:double cosinmn=Double.parseDouble(Exp);
                            double cosin =cosinmn*0.0174532925;
                            String cosine = "https://newton.now.sh/cos/"+cosin;
                            Json(cosine);
                            break;
                        case 7:double tanmn=Double.parseDouble(Exp);
                            double tane =tanmn*0.0174532925;
                            String tan = "https://newton.now.sh/tan/"+tane;
                            Json(tan);
                            break;
                        case 8:double insinmn=Double.parseDouble(Exp);
                            double insin =insinmn*0.0174532925;
                            String insine = "https://newton.now.sh/arcsin/"+insin;
                            Json(insine);
                            break;
                        case 9:double incosmn=Double.parseDouble(Exp);
                            double incos =incosmn*0.0174532925;
                            String incosine = "https://newton.now.sh/arccos/"+incos;
                            Json(incosine);
                            break;
                        case 10:double intanemn=Double.parseDouble(Exp);
                            double intane =intanemn*0.0174532925;
                            String intan = "https://newton.now.sh/arctan/"+intane;
                            Json(intan);
                            break;
                        case 11:String avalue = "https://newton.now.sh/abs/"+Exp;
                            Json(avalue);
                            break;
                        case 12:
                            String loga = "https://newton.now.sh/log/"+Exp;
                            Json(loga);
                            break;
                        case 13:String tangent = "https://newton.now.sh/tangent/"+Exp;
                            Json(tangent);
                            break;
                        case 14:String area = "https://newton.now.sh/area/"+Exp;
                            Json(area);
                            break;

                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.animate));

                exp.setText("");
                show.setText("");
                startval.setText("");
                endval.setText("");
                fofx.setText("");
                fx.setText("");
                value.setText("");
                base.setText("");
                valx.setText("");
                submit.setText("Submit");
            }
        });

    }

    public void Json(String link){
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jor = new JsonObjectRequest(link,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String result = response.getString("result");

                    show.setText(result);
                    show.setVisibility(View.VISIBLE);

                    submit.setText("Search Again");

                    //Toast.makeText(Simplify.this,result, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please Enter a valid Expression!", Toast.LENGTH_SHORT).show();

                submit.setText("Try Again");

            }


        });
        rq.add(jor);
    }


}
