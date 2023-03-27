#version 400

uniform sampler2D texture;
uniform float fade_timer_global;
uniform float fade_timer_local;

in vec2 texcoord_final;
flat in uint texid_final;

void main()
{
    vec4 c = texture2D (texture, texcoord_final);
	if (c.w == 0) {
		discard;
	}
    gl_FragColor = mix (vec4 (c.x, c.y, c.z, (1.0 - fade_timer_local) * c.w), vec4 (0.0, 0.0, 0.0, 1.0), fade_timer_global);
}