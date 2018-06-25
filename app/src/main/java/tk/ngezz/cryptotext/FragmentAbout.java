package tk.ngezz.cryptotext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Erikodiony on 3/1/2018.
 */

public class FragmentAbout extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.about_fragment,container,false);

        /* Define Your Functionality Here
           Find any view  => v.findViewById()
          Specifying Application Context in Fragment => getActivity() */

        TextView tLink1 = (TextView) v.findViewById(R.id.PI_Title1_Body3);
        tLink1.setText(Html.fromHtml("<a href=http://erikodiony.github.io> ERIKODIONY ARIESSA WAHYUDI"));
        tLink1.setMovementMethod(LinkMovementMethod.getInstance());


        return v;
    }
}
