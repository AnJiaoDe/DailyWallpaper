#include <jni.h>
#include <string>

using namespace std;
extern "C"
jstring Java_com_cy_dailywallpaper_WelcomeActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//
//////返回一个字符串数组
//jobjectArray Java_com_cy_dailywallpaper_model_JniUtils_getApiAddress(JNIEnv *env, jobject) {
//    jstring str;
//    jobjectArray jobjArr = NULL;
//    jsize len = 5;
//    char *c[] = {"http://cn.bing.com/HPImageArchive.aspx?format=js", "world!", " JNI", " is", " fun"};
//    jobjArr = (env)->NewObjectArray(len, (env)->FindClass("java/lang/String"), 0);
//    for (int i = 0; i < len; i++) {
//        str = (env)->NewStringUTF(c[i]);
//        (env)->SetObjectArrayElement(jobjArr, i, str);
//    }
//    return jobjArr;
//}
//jobject CppMap2JMap(JNIEnv *env,map) {
//
//    jclass jmapclass = env->FindClass("java/util/Map");
//    jmethodID mid = env->GetMethodID(jmapclass, "<init>", "()V");
//    jmethodID putmethod = env->GetMethodID(jmapclass, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
//    jobject jmap = env->NewObject(jmapclass,mid);
//    jclass strClass = env->FindClass("java/lang/String");
//    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
//    jstring encoding = env->NewStringUTF("utf-8");
//    std::map<char*,char*>::iterator it;
//    for(it=cmap.begin();it != cmap.end();it++)
//    {
//        char* key=it->first;
//        jbyteArray keybytes = env->NewByteArray(strlen(key));
//        env->SetByteArrayRegion(keybytes, 0, strlen(key), (jbyte*)key);
//        jstring jkey = (jstring)env->NewObject(strClass, ctorID, keybytes, encoding);
//        char* value =it->second;
//        jbyteArray valuebytes = env->NewByteArray(strlen(value));
//        env->SetByteArrayRegion(valuebytes, 0, strlen(value), (jbyte*)value);
//        jstring jvalue = (jstring)env->NewObject(strClass, ctorID, valuebytes, encoding);
//        env->CallVoidMethod(jmap,putmethod,jkey,jvalue);
//    }
//    return jmap;
//}
///**
// * 工具方法，把java的string类型转化成 c的str
// */
//char* Jstring2CStr(JNIEnv* env, jstring jstr) {
//    char* rtn = NULL;
//    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
//    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
//    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
//                                        "(Ljava/lang/String;)[B");
//    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
//                                                            strencode); // String .getByte("GB2312");
//    jsize alen = (*env)->GetArrayLength(env, barr);
//    jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
//    if (alen > 0) {
//        rtn = (char*) malloc(alen + 1); //"\0"
//        memcpy(rtn, ba, alen);
//        rtn[alen] = '\0';
//    }
//    (*env)->ReleaseByteArrayElements(env, barr, ba, 0); //释放内存
//    return rtn;
//}