#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_spotifysearch_utils_Const_getClientId(
        JNIEnv* env,
        jclass clazz) {
    std::string clientId = "a75ec794a8344c8b993380ef080efe54";
    return env->NewStringUTF(clientId.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_spotifysearch_utils_Const_getClientSecret(
        JNIEnv* env,
        jclass clazz) {
    std::string clientSecret = "99737a2b81da447f89b41f66b898c6e6";
    return env->NewStringUTF(clientSecret.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_spotifysearch_utils_Const_getRedirectUrl(
        JNIEnv* env,
        jclass clazz) {
    std::string url = "mohsen://mohsenspotifyredirect.com/mohsen/";
    return env->NewStringUTF(url.c_str());
}
