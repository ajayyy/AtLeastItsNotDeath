varying vec4 vColor;
varying vec2 vTexCoord;
varying float distance;

uniform sampler2D u_texture;
uniform vec2 position;

//from https://stackoverflow.com/questions/15095909/from-rgb-to-hsv-in-opengl-glsl
vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

//from https://stackoverflow.com/questions/15095909/from-rgb-to-hsv-in-opengl-glsl
vec3 rgb2hsv(vec3 c) {
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

void main() {
	
	vec4 color = texture2D(u_texture, vTexCoord) * vColor;
	
	vec3 hsvColor = rgb2hsv(vec3(color.r, color.g, color.b));
	vec3 newColor = hsv2rgb(vec3(hsvColor.x, hsvColor.y, 20. / distance));
	
	gl_FragColor = vec4(newColor.r, newColor.g, newColor.b, color.a);
}