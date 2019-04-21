#include <jni.h>
#include <string>

using namespace std;
//extern "C"
//jstring Java_com_cy_dailywallpaper_model_JniUtils_stringFromJNI(
//        JNIEnv *env,
//        jobject /* this */) {
//    string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}
//extern "C"
//jobjectArray Java_com_cy_dailywallpaper_model_JniUtils_getStructArray(JNIEnv *env, jobject jobj) {
//    jobjectArray infos = NULL;    // jobjectArray 为指针类型
//    jclass clsDiskInfo = NULL;        // jclass 为指针类型
//    jobject obj;
//    jfieldID nameID;
//    jfieldID serialNoID;
//    jmethodID consID;
//    jsize len;
//    int i;
//
//    clsDiskInfo = env->FindClass("com/cy/dailywallpaper/DiskInfo");//必须完整类路径
//    len = 5;
//    infos = env->NewObjectArray(len, clsDiskInfo, NULL);
//    nameID = env->GetFieldID(clsDiskInfo, "name", "Ljava/lang/String;");
//    serialNoID = env->GetFieldID(clsDiskInfo, "serialNo", "I");
//    consID = env->GetMethodID(clsDiskInfo, "<init>", "()V");
//    for (i = 0; i < len; i++) {
//        obj = env->NewObject(clsDiskInfo, consID);
//        env->SetObjectField(obj, nameID, env->NewStringUTF("disk"));
//        env->SetIntField(obj, serialNoID, i);
//        env->SetObjectArrayElement(infos, i, obj);
//    }
//    return infos;
//}
extern "C"
jobject Java_com_cy_dailywallpaper_JniUtils_getHashMap(JNIEnv *env,jobject jobj) {
    jclass class_hashmap = env->FindClass("java/util/HashMap");
    jmethodID hashmap_init = env->GetMethodID(class_hashmap, "<init>",
                                              "()V");
    jobject HashMap = env->NewObject(class_hashmap, hashmap_init, "");
    jmethodID HashMap_put = env->GetMethodID(class_hashmap, "put",
                                             "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    env->CallObjectMethod(HashMap, HashMap_put,
                          env->NewStringUTF("bing_today"),
                          env->NewStringUTF("http://cn.bing.com/HPImageArchive.aspx"));
    env->CallObjectMethod(HashMap, HashMap_put,
                          env->NewStringUTF("key2"),
                          env->NewStringUTF("value2"));
    return HashMap;
}
////返回一个字符串数组
//extern "C"
//jobjectArray Java_com_cy_dailywallpaper_model_JniUtils_getApiAddress(JNIEnv *env, jobject) {
//    jstring str;
//    jobjectArray jobjArr = NULL;
//    char *c[] = {"ty5765a", "5768b", "c787i", "7989d", "\0"};
//    jsize len = 4;
//    jobjArr = (env)->NewObjectArray(len, (env)->FindClass("java/lang/String"), 0);
//    for (int i = 0; i < len; i++) {
//        str = (env)->NewStringUTF(c[i]);
//        (env)->SetObjectArrayElement(jobjArr, i, str);
//    }
//    return jobjArr;
//}
