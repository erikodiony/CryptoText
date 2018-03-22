package tk.ngezz.cryptotext;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Erikodiony on 3/1/2018.
 */

public class FragmentEncrypt extends Fragment {

    //VARIABLE
    private Button btn_open_txt;
    private Button btn_open_key;
    private Button btn_exec_txt;
    private Button btn_exec_copy;
    private Button btn_exec;
    private Button btn_save_toTxt;
    private Button btn_clear_txt;

    private EditText et_PE;
    private EditText et_RESULT_PE;

    private TextView txtPathLocation;
    private TextView keyPathLocation;

    public static final int FILE_SELECT_TXT_CODE = 69;
    public static final int FILE_SELECT_KEY_CODE = 96;
    public static final String TXT_MIME = "text/plain";
    public static final String KEY_MIME = "*/*";

    public static Uri keyPath;
    public static Uri txtPath;

    //PAGE ELEMENT
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.encrypt_fragment,container,false);
        setHasOptionsMenu(true);

        btn_open_txt = (Button) v.findViewById(R.id.Title1_btn1_PE);
        btn_open_key = (Button) v.findViewById(R.id.Title2_btn1_PE);

        btn_clear_txt = (Button) v.findViewById(R.id.Title1_btn3_PE);

        btn_exec_txt = (Button) v.findViewById(R.id.Title1_btn2_PE);
        btn_exec_copy = (Button) v.findViewById(R.id.Title4_btn1_PE);

        btn_exec = (Button) v.findViewById(R.id.Title3_btn1_PE);

        btn_save_toTxt = (Button) v.findViewById(R.id.Title4_btn2_PE);

        et_PE = (EditText) v.findViewById(R.id.editText_PE);
        et_RESULT_PE = (EditText) v.findViewById(R.id.editText_Result_PE);

        txtPathLocation = (TextView) v.findViewById(R.id.textPath_PD);
        keyPathLocation = (TextView) v.findViewById(R.id.keyPath_PE);

        //Handle Button Open Plain Text File
        btn_open_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessStorage(FILE_SELECT_TXT_CODE) == true) {
                    showFileExplorer(FILE_SELECT_TXT_CODE,TXT_MIME);
                }
            }
        });

        //Handle EditText PlainText
        et_PE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        view.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;

            }
        });

        //Handle EditText EncryptText
        et_RESULT_PE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        view.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        //Handle Button Save PlainText
        btn_exec_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_PE.getText().length() == 0) {
                    Toast.makeText(getContext(), R.string.toast_editText_wasEmpty, Toast.LENGTH_SHORT).show();
                }
                else {
                    btn_open_txt.setEnabled(false);
                    btn_exec_txt.setEnabled(false);
                    et_PE.setFocusable(false);
                }
            }
        });

        //Handle Button Clear PlainText
        btn_clear_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_exec_txt.setEnabled(true);
                btn_open_txt.setEnabled(true);
                et_PE.setFocusable(true);
                et_PE.setFocusableInTouchMode(true);
                et_PE.setText("");
                txtPathLocation.setText(R.string.title1_path_pe);
            }
        });

        //Handle Button Open Key File
        btn_open_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessStorage(FILE_SELECT_KEY_CODE) == true) {
                    showFileExplorer(FILE_SELECT_KEY_CODE,KEY_MIME);
                }
            }
        });

        //Handle Button MAIN EXECUTE
        btn_exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyPathLocation.getText().toString().equals("Path : -") || btn_exec_txt.isEnabled()) {
                    Toast.makeText(getContext(),R.string.toast_exec_err,Toast.LENGTH_SHORT).show();
                }
                else {
                    String res = RUNNER_EXEC.encrypt(Builder_Key.key_param, Converter_Unicode.encode(et_PE.getText().toString()));
                    et_RESULT_PE.setText(res);
                    et_RESULT_PE.setFocusable(false);
                    Toast.makeText(getContext(), R.string.toast_encrypt_ok,Toast.LENGTH_SHORT).show();
                }
            }
        });

        //HANDLE Button COPY ALL
        btn_exec_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_RESULT_PE.getText().length() == 0) {
                    Toast.makeText(getContext(), R.string.toast_copy_encrypt_err, Toast.LENGTH_SHORT).show();
                } else {
                    ClipboardManager cb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("encrypt",et_RESULT_PE.getText().toString());
                    cb.setPrimaryClip(clip);
                    Toast.makeText(getContext(), R.string.toast_copy_encrypt_ok, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //HANDLE Button SAVE AS FILE
        btn_save_toTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_RESULT_PE.getText().length() == 0) {
                    Toast.makeText(getContext(), R.string.toast_saveAs_encrypt_err, Toast.LENGTH_SHORT).show();
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    try {
                        FileWriter fw = new FileWriter(Environment.getExternalStorageDirectory() + "/CryptoText/" + "Encrypt__" + date + ".encx");
                        fw.write(et_RESULT_PE.getText().toString());
                        fw.flush();
                        fw.close();
                        Toast.makeText(getContext(),"Encrypt__" + date + ".encx" + " was created on CryptoText Folder . . .", Toast.LENGTH_LONG).show();
                    }
                    catch (IOException e) {
                        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }

    //LOCAL FUNCTION
    private boolean accessStorage(int Code) {
        if (Checker_Permission.checking(getActivity()) == true) {
            return true;
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Code); //CALL void onRequestPermissionResult
            return false;
        }
    }

    private void showFileExplorer(int Code, String Mime) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType(Mime);
        try {
            startActivityForResult(i,Code); //CALL void onActivityResult
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(),"Need File Manager App to execute . . .",Toast.LENGTH_SHORT).show();
        }
    }

    //TRIGGER EVENT
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clearAll:
                btn_exec_txt.setEnabled(true);
                btn_open_txt.setEnabled(true);
                et_PE.setFocusable(true);
                et_PE.setFocusableInTouchMode(true);
                et_PE.setText("");
                et_RESULT_PE.setText("");
                txtPathLocation.setText(R.string.title1_path_pe);
                keyPathLocation.setText(R.string.title2_path_pe);

                Toast.makeText(getContext(),R.string.toast_clearAllData,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_TXT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    txtPath = data.getData();
                    txtPathLocation.setText("Path : " + txtPath.getPath());
                    et_PE.setText(RUNNER_EXEC.readFile(txtPath,getContext()));
                }
                break;
            case FILE_SELECT_KEY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    keyPath = data.getData();
                    if (!Builder_Key.check_Key(keyPath,getContext())) {
                        keyPathLocation.setText(R.string.title2_path_pe);
                        Toast.makeText(getContext(), R.string.toast_key_err3, Toast.LENGTH_SHORT).show();
                    } else {
                        keyPathLocation.setText("Path : " + keyPath.getPath());
                        Toast.makeText(getContext(), R.string.toast_key_ok2, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case FILE_SELECT_TXT_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showFileExplorer(FILE_SELECT_TXT_CODE, TXT_MIME);
                } else {
                    Toast.makeText(getContext(),R.string.toast_permission_storage,Toast.LENGTH_SHORT).show();
                }
                break;
            case FILE_SELECT_KEY_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showFileExplorer(FILE_SELECT_KEY_CODE, KEY_MIME);
                } else {
                    Toast.makeText(getContext(),R.string.toast_permission_storage,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
