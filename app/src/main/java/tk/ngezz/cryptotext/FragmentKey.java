package tk.ngezz.cryptotext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Erikodiony on 3/1/2018.
 */

public class FragmentKey extends Fragment {

    //VARIABLE
    public static final int PERMISSION_REQUEST_CODE = 1;
    public final int[] TAG_ROT13 = new int[2];
    public final int[] TAG_ELGamal = new int[3];

    //PAGE ELEMENT
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.key_fragment, container, false);

        /* Define Your Functionality Here
           Find any view  => v.findViewById()
          Specifying Application Context in Fragment => getActivity() */

        /* Button */
        final Button btn_exec_PK = (Button) v.findViewById(R.id.title4_btn1_PK);

        /* Switch*/
        final Switch sw_enable_ROT13 = (Switch) v.findViewById(R.id.switch1_ROT13);
        final Switch sw_enable_ELGAMAL = (Switch) v.findViewById(R.id.switch1_ELGAMAL);

        final Switch sw_Reverse = (Switch) v.findViewById(R.id.switch2_ROT13);

        final Switch sw_Priv = (Switch) v.findViewById(R.id.switch2_ELGAMAL);
        final Switch sw_Pub = (Switch) v.findViewById(R.id.switch3_ELGAMAL);

        /*EditText*/
        final EditText et_Priv = (EditText) v.findViewById(R.id.editText_PrivateKey_PK);
        final EditText et_Pub = (EditText) v.findViewById(R.id.editText_PublicKey_PK);

        /*Relative Layout*/
        final RelativeLayout rl_Priv = (RelativeLayout) v.findViewById(R.id.PK_Title2_RL2_Child);
        final RelativeLayout rl_Pub = (RelativeLayout) v.findViewById(R.id.PK_Title2_RL3_Child);

        /* Event Trigger (Switch)*/
        sw_enable_ROT13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw_enable_ROT13.isChecked() == false) {
                    sw_Reverse.setEnabled(false);
                } else {
                    sw_Reverse.setEnabled(true);
                }
            }
        });

        sw_enable_ELGAMAL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw_enable_ELGAMAL.isChecked() == false) {
                    sw_Priv.setEnabled(false);
                    sw_Pub.setEnabled(false);
                    et_Priv.setEnabled(false);
                    et_Pub.setEnabled(false);
                } else {
                    sw_Priv.setEnabled(true);
                    sw_Pub.setEnabled(true);
                    et_Priv.setEnabled(true);
                    et_Pub.setEnabled(true);
                }
            }
        });

        sw_Priv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw_Priv.isChecked() == false) {
                    rl_Priv.setVisibility(View.VISIBLE);
                } else {
                    rl_Priv.setVisibility(View.GONE);
                }
            }
        });

        sw_Pub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw_Pub.isChecked() == false) {
                    rl_Pub.setVisibility(View.VISIBLE);
                } else {
                    rl_Pub.setVisibility(View.GONE);
                }
            }
        });

        btn_exec_PK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sw_enable_ROT13.isChecked() == false) {
                    TAG_ROT13[0] = 0;
                    TAG_ROT13[1] = 0;
                } else {
                    TAG_ROT13[0] = 1;
                    if (sw_Reverse.isChecked() == true) {
                        TAG_ROT13[1] = 1;
                    } else {
                        TAG_ROT13[1] = 0;
                    }
                }

                if (sw_Pub.isChecked() == true) {
                    TAG_ELGamal[2] = Builder_PrimeNumber.CreateRandomKey("PublicKey");
                } else {
                    if (et_Pub.getText().length() == 0) {
                        et_Pub.setError(getString(R.string.tbox_null));
                        TAG_ELGamal[2] = 0;
                    } else {
                        if (Integer.parseInt(et_Pub.getText().toString()) < 255) {
                            et_Pub.setError(getString(R.string.key_Pub_OutOfRange));
                            TAG_ELGamal[2] = 0;
                        } else {
                            if (!Builder_PrimeNumber.Check_PublicKey(Integer.parseInt(et_Pub.getText().toString()))) {
                                et_Pub.setError(getString(R.string.key_Pub_NotPrime));
                                TAG_ELGamal[2] = 0;
                            } else {
                                TAG_ELGamal[2] = Integer.parseInt((et_Pub.getText().toString()));
                            }
                        }
                    }
                }

                if (sw_enable_ELGAMAL.isChecked() == false) {
                    TAG_ELGamal[0] = 0;
                    TAG_ELGamal[1] = Builder_PrimeNumber.CreateRandomKey("PrivateKey");
                    TAG_ELGamal[2] = Builder_PrimeNumber.CreateRandomKey("PublicKey");
                } else {
                    TAG_ELGamal[0] = 1;

                    if (sw_Priv.isChecked() == true) {
                        TAG_ELGamal[1] = Builder_PrimeNumber.CreateRandomKey("PrivateKey");
                    } else {
                        if (et_Priv.getText().length() == 0) {
                            et_Priv.setError(getString(R.string.tbox_null));
                            TAG_ELGamal[1] = 0;
                        } else {
                            if (!Builder_PrimeNumber.Check_PrivateKey(Integer.parseInt(et_Priv.getText().toString()), TAG_ELGamal[2])) {
                                et_Priv.setError(getString(R.string.key_Priv_OutOfRange));
                                TAG_ELGamal[1] = 0;
                            } else {
                                TAG_ELGamal[1] = Integer.parseInt(et_Priv.getText().toString());
                            }
                        }
                    }
                }

                if (TAG_ELGamal[1] == 0 || TAG_ELGamal[2] == 0) {
                    Toast.makeText(getContext(), R.string.toast_key_err, Toast.LENGTH_LONG).show();
                } else {
                    accessStorage();
                }
            }
        });

        return v;
    }

    //LOCAL FUNCTION
    public void accessStorage() {
        if (Checker_Permission.checking(getActivity()) != true) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE); //CALL void onRequestPermissionResult
        } else {
            RUNNER_EXEC.createDir();
            Builder_Key.writeKey(getContext(),TAG_ROT13,TAG_ELGamal);
        }
    }

    //TRIGGER EVENT
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accessStorage();
                }
                else {
                    Toast.makeText(getContext(),R.string.toast_permission_storage,Toast.LENGTH_SHORT).show();
                }
        }
    }
}
