smaliDeObfuscator
=================
A gui tool develop in java swing to modify smali files, to rename package name, class name, field name and function name. the smali file which is generated by apktool(http://code.google.com/p/android-apktool/).

You Can Use It In Following Ways: 
1. it can automatic rename the class names, which add postfix of its super class，for example：
a.smali：
.class public final Lcom/b/b/a;
.super Ljava/lang/Object;
...

will be renamed to a_Object.smali and any places refrence to it are changed also.

2. You can rename(click one item and press 'F2'), rename package, class names, function names as you wish.

3. it can also change the private function and field to public.(up-public)

Notice：
1. You must assgin the root smali path when load smali
2. When you rename, the smali dir will be modifed immediately and can't Ctrl+Z

How to Use：
run java application -》 SmaliMain


------------------------
smaliDeObfuscatorui采用swing实现，可以解析smali目录。它可以让你基于apktool反编译得到的smali目录进行对包名，类名，函数名或变量名进行改名或自动改名。smali目录中，所改动的包，类，函数，变量涉及到的引用代码也会自动改名。（类似于eclipse中的自动改名）。
自动改名可以对类似a.b.c.d这样的短字符类进行自动处理，在类名后面自动加上"_父类名"，例如：
文件a.smali（包名com.b.b）：
.class public final Lcom/b/b/a;
.super Ljava/lang/Object;
...
将被自动改名为com.b.b.a_Object（文件名也被会改成com.b.b.a_Object.smali）

自动提权：将所有private的变量和方法改成public类型

注意：
1. 加载时必须指定正确的smali根目录
2. 所有改名，up-public操作都在原始smali目录进行，不能撤销。

使用方法：
run java application -》 SmaliMain


