package com.qingmei2.rximagepicker_extension.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qingmei2.rximagepicker.core.ActivityPickerProjector;
import com.qingmei2.rximagepicker_extension.R;
import com.qingmei2.rximagepicker_extension.entity.SelectionSpec;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ZhihuImagePickerActivity extends AppCompatActivity {

    private ZhihuImagePickerFragment fragment;

    public static final int REQUEST_CODE_PREVIEW = 23;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(SelectionSpec.getInstance().themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker_holder);
        displayPickerView();
    }

    private void displayPickerView() {
        fragment = new ZhihuImagePickerFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, fragment)
                .commit();

        fragment.pickImage()
                .subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uri) throws Exception {
                        ActivityPickerProjector.getInstance().emitUri(uri);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ActivityPickerProjector.getInstance().emitError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        closure();
                    }
                });
    }

    public void closure() {
        ActivityPickerProjector.getInstance().endUriEmitAndReset();
        finish();
    }

    @Override
    public void onBackPressed() {
        closure();
    }

}