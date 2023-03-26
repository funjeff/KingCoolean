#version 400

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texcoord;

uniform mat4 transform;
uniform mat4 vp;

out vec2 texcoord_final;

void main()
{
	texcoord_final = texcoord;
	vec4 pos = vp * vec4(position, 1.0);
    gl_Position = vec4 (pos.x, pos.y, pos.z, pos.w);
}