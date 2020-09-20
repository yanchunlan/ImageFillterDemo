precision mediump float;

uniform sampler2D InputImageTexture2;
uniform sampler2D InputImageTexture;
uniform lowp float intensity;

varying vec2 TextureCoordinate;

void main() {
    // 取出当前像素的纹素
    highp vec4 textureColor = texture2D(InputImageTexture, TextureCoordinate);
    highp float blueColor = textureColor.b * 63.0;

    // 计算B通道，看使用哪个像素色块（这里分别对计算结果向上，向下取整，然后再对两者进行线性计算，减小误差）
    highp vec2 quad1;
    quad1.y = floor(floor(blueColor) / 8.0);
    quad1.x = floor(blueColor) - (quad1.y * 8.0);

    highp vec2 quad2;
    quad2.y = floor(ceil(blueColor) / 8.0);
    quad2.x = ceil(blueColor) - (quad2.y * 8.0);

    // 计算R、G通道
    highp vec2 texPos1;
    texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);
    texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);

    highp vec2 texPos2;
    texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);
    texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);

    // 根据转换后的纹理坐标，在基准图上取色
    lowp vec4 newColor1 = texture2D(InputImageTexture2, texPos1);
    lowp vec4 newColor2 = texture2D(InputImageTexture2, texPos2);

    // 对计算出来的两个色值，线性求平均(fract：取小数点后值)
    lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));

    // intensity 按需计算滤镜透明度，混合计算前后的色值
    gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), intensity);
}