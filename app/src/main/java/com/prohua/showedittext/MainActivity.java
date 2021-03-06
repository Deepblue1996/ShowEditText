package com.prohua.showedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.prohua.dpedittext.DpEditText;

public class MainActivity extends AppCompatActivity {

    private DpEditText dpEditText;
    private TextView changeTextView;
    private TextView searchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dpEditText = (DpEditText) findViewById(R.id.dp_edit);
        changeTextView = (TextView) findViewById(R.id.change);
        searchTextView = (TextView) findViewById(R.id.search);
        dpEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        dpEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        dpEditText.addTextSearchListener(new DpEditText.TextSearchListener() {
            @Override
            public void onNext() {
                searchTextView.setText("点击了搜索");
            }
        });
        dpEditText.addTextChangedListener(new DpEditText.TextChangedListener() {
            @Override
            public void onTextChanged(CharSequence charSequence) {
                changeTextView.setText(charSequence);
            }
        });
    }
}
