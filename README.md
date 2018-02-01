# ShowEditText

## 搜索框 Search box widget

<pre><code>param left_icon Left icon
param right_icon Right icon
param bg_color widget background
param left_img_size Left icon Size
param right_img_size Right icon Size
param edit_text_size EditText Text Size
param hint_text EditText Text Content

Interface TextChangedListener Seeing the name of a thing one thinks of its function
method onTextChanged Give you textChanged Content
Interface addTextSearchListener Seeing the name of a thing one thinks of its function
method onNext Next Action</code></pre>

Created by Deep on 2017/10/27 0027.
 
## How to

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}Copy
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.Deepblue1996:ShowEditText:1.0.0'
	}
