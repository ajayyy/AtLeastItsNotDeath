attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

varying vec4 vColor;
varying vec2 vTexCoord;
varying float distance;
uniform vec2 position;

void main() {

	vTexCoord = a_texCoord0;
	
	//calculate how far away this pixel is from the player
	vec3 actualPosition = vec3(a_position.x + vTexCoord.x, a_position.y + vTexCoord.y, a_position.z);
	distance = sqrt( pow(position.x - actualPosition.x, 2.) + pow(position.y - actualPosition.y, 2.));
	
	vColor = a_color;
	
	gl_Position = u_projTrans * vec4(a_position, 1.);
	
}