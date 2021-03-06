package com.prohua.dpedittext;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

/**
 * 搜索框
 * Search box widget
 *
 * @param left_icon      Left icon
 * @param right_icon     Right icon
 * @param bg_color       widget background
 * @param left_img_size  Left icon Size
 * @param right_img_size Right icon Size
 * @param edit_text_size EditText Text Size
 * @param hint_text      EditText Hint Text Content
 * @param edit_text      EditText Text Content
 * @Interface TextChangedListener Seeing the name of a thing one thinks of its function
 * @method onTextChanged Give you textChanged Content
 * @Interface addTextSearchListener Seeing the name of a thing one thinks of its function
 * @method onNext Next Action
 * <p>
 * Created by Deep on 2017/10/27 0027.
 */

public class DpEditText extends RelativeLayout {

    private EditText editText;
    private boolean show = false;

    private ImageView rightImg;

    public DpEditText(Context context) {
        super(context);
        init(context, null);
    }

    public DpEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DpEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DpEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.receive, null);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.DpEditText);

        int leftIcon = 0, rightIcon = 0, mTextSize = 12, leftSize = 25, rightSize = 25;
        String hintText = "", editTextContent = "";

        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.DpEditText_left_icon) {
                leftIcon = typedArray.getResourceId(attr, R.drawable.ic_search);

            } else if (attr == R.styleable.DpEditText_right_icon) {
                rightIcon = typedArray.getResourceId(attr, R.drawable.ic_clean);

            } else if (attr == R.styleable.DpEditText_edit_text) {
                editTextContent = typedArray.getString(attr);

            } else if (attr == R.styleable.DpEditText_edit_text_size) {
                mTextSize = (int) typedArray.getDimension(attr, 12);

            } else if (attr == R.styleable.DpEditText_hint_text) {
                hintText = typedArray.getString(attr);

            } else if (attr == R.styleable.DpEditText_left_img_size) {
                leftSize = (int) typedArray.getDimension(attr, 25);

            } else if (attr == R.styleable.DpEditText_right_img_size) {
                rightSize = (int) typedArray.getDimension(attr, 25);

            }
        }

        if(rightIcon == 0) {
            rightIcon = R.drawable.ic_clean;
        }


        typedArray.recycle();

        editText = myView.findViewById(R.id.edit);
        ImageView leftImg = myView.findViewById(R.id.left);
        rightImg = myView.findViewById(R.id.right);

        editText.setHint(hintText);
        editText.setText(editTextContent);
        editText.setTextSize(px2dip(context, mTextSize));

        if (leftIcon == 0) {
            leftImg.setVisibility(GONE);
        } else {
            leftImg.setImageResource(leftIcon);
        }
        if (rightIcon == 0) {
            rightImg.setVisibility(GONE);
        } else {
            rightImg.setImageResource(rightIcon);
        }
        if (editTextContent.length() > 0) {
            rightImg.setVisibility(VISIBLE);
        }

        LayoutParams para = (LayoutParams) leftImg.getLayoutParams();
        para.width = leftSize;
        para.height = leftSize;
        leftImg.setLayoutParams(para);

        LayoutParams para2 = (LayoutParams) rightImg.getLayoutParams();
        para2.width = rightSize;
        para2.height = rightSize;
        rightImg.setLayoutParams(para2);

        rightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                /*ObjectAnimator anim1 = ObjectAnimator.ofFloat(rightImg, "alpha",
                        1.0f, 0f);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(rightImg, "scale",
                        1.0f, 0.8f);
                AnimatorSet animSet = new AnimatorSet();
                animSet.setDuration(200);
                animSet.setInterpolator(new LinearInterpolator());
                //两个动画同时执行
                animSet.playTogether(anim1, anim2);
                animSet.start();

                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rightImg.setVisibility(GONE);
                    }
                });*/
                ViewAnimator
                        .animate(rightImg)
                        .waitForHeight()
                        .alpha(1, 0)
                        .scale(1.0f, 0.8f)
                        .onStop(new AnimationListener.Stop() {
                            @Override
                            public void onStop() {

                            }
                        })
                        .duration(200)
                        .start();
                show = false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (textSearchListener != null) {
                    textSearchListener.onNext();
                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textChangedListener != null) {
                    textChangedListener.onTextChanged(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!show && temp.length() > 0) {
                    rightImg.setVisibility(VISIBLE);
                    ViewAnimator
                            .animate(rightImg)
                            .waitForHeight()
                            .alpha(0, 1)
                            .scale(0.8f, 1.0f)
                            .duration(200)
                            .start();
                    show = true;
                } else if (temp.length() == 0) {
                    ViewAnimator
                            .animate(rightImg)
                            .waitForHeight()
                            .alpha(1, 0)
                            .scale(1.0f, 0.8f)
                            .onStop(new AnimationListener.Stop() {
                                @Override
                                public void onStop() {
                                    rightImg.setVisibility(GONE);
                                }
                            })
                            .duration(200)
                            .start();
                    show = false;
                }

            }
        });

        addView(myView);
    }

    public void setEditText(String editTextContent) {
        editText.setText(editTextContent);
        if("".equals(editTextContent)) {
            ViewAnimator
                    .animate(rightImg)
                    .waitForHeight()
                    .alpha(1, 0)
                    .scale(1.0f, 0.8f)
                    .onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {

                        }
                    })
                    .duration(200)
                    .start();
            show = false;
        }
    }

    public void setEditTextHint(String editTextContent) {
        editText.setHint(editTextContent);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    public void setImeOptions(int imeOptions) {
        editText.setImeOptions(imeOptions);
    }

    public String getEditContent() {
        return editText.getText().toString();
    }

    private TextChangedListener textChangedListener;

    public void addTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public interface TextChangedListener {
        void onTextChanged(CharSequence charSequence);
    }

    private TextSearchListener textSearchListener;

    public void addTextSearchListener(TextSearchListener textSearchListener) {
        this.textSearchListener = textSearchListener;
    }

    public interface TextSearchListener {
        void onNext();
    }
}
