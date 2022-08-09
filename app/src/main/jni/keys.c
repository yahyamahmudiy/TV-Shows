#include <jni.h>
JNIEXPORT jstring JNICALL
Java_com_example_imperative_networking_Server_Pruduction(JNIEnv *env, jobject instance) {
    return (*env)->  NewStringUTF(env, "https://www.episodate.com/");
}

JNIEXPORT jstring JNICALL
Java_com_example_imperative_networking_Server_Development(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "https://www.episodate.com/");
}