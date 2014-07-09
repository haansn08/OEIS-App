package com.shaan.oeis;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        assert view != null;
        TextView oeisLicenseText = (TextView)view.findViewById(R.id.about_oeis_license);
        oeisLicenseText.setText(Html.fromHtml(getString(R.string.oeis_license)));
        oeisLicenseText.setMovementMethod(LinkMovementMethod.getInstance());


        TextView oeisTitle = (TextView)view.findViewById(R.id.about_title);
        oeisTitle.setText(Html.fromHtml(getString(R.string.oeis_written)));
        oeisTitle.setMovementMethod(LinkMovementMethod.getInstance());

        TextView appLicenseText = (TextView)view.findViewById(R.id.about_app_license);
        appLicenseText.setText(Html.fromHtml(getString(R.string.app_license)));
        appLicenseText.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
