// src/main/rs/sharpen.rs
#pragma version(1)
#pragma rs java_package_name(com.example.yourapp)

float gCoeff;

uchar4 RS_KERNEL filter(uchar4 in) {
    float4 pixel = rsUnpackColor8888(in);

    // Simple sharpening logic, subtle effect
    pixel.r = clamp(pixel.r * (1.0f + gCoeff) - gCoeff, 0.0f, 1.0f);
    pixel.g = clamp(pixel.g * (1.0f + gCoeff) - gCoeff, 0.0f, 1.0f);
    pixel.b = clamp(pixel.b * (1.0f + gCoeff) - gCoeff, 0.0f, 1.0f);

    return rsPackColorTo8888(pixel);
}

void root(const uchar4* v_in, uchar4* v_out) {
    float4 f4 = rsUnpackColor8888(*v_in);
    float3 result = f4.rgb;

    result = clamp(result * (1.0f + gCoeff) - gCoeff, 0.0f, 1.0f);

    *v_out = rsPackColorTo8888(result);
}

void filter() {
    rsForEach(gScript, gIn, gOut);
}
