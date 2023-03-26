#version 400

uniform sampler2D texture;

in vec2 texcoord_final;
flat in uint texid_final;

void main()
{
    vec4 c = texture2D (texture, texcoord_final);
    gl_FragColor = vec4 (c.x, c.y, c.z, 1.0);
}