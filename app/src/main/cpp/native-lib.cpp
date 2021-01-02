//
// Created by Edward on 02/01/2021.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jlong JNICALL
Java_id_ac_ui_cs_mobileprogramming_edwardpga_wisecompanion_CompanionFragment_addLongValue(
        JNIEnv *env,
        jobject,
        jlong val1,
        jlong val2) {
    return (val1 + val2);
}