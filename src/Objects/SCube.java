package Objects;

public class SCube extends SObject{
	private float length;
		
	public SCube(){	
		super();
		init();
		update();
	}
		
	public SCube(float length){
		super();
		init();
		this.length = length;
		update();
	}
	
	
	
	private void init(){
		this.length = 1;
	}


	@Override
	protected void genData() {
		length=length/2;
		numVertices = 24; 
		vertices = new float[numVertices*3];

		
		float [][] cube_vertex ={
				 {-length, -length, -length},
				 {length, -length, -length},
			     {-length,   length, -length},
			      {length,   length, -length},
			     {-length, -length,   length},
			      {length, -length,   length},
			     {-length,   length,   length},
			      {length,   length,   length}
	};
		//Back
		int k=0;
		for(int i=0;i<2;i++) {
			vertices[k++]=cube_vertex[0][i];
		}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[2][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[3][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[1][i];
			}
		
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[0][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[4][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[6][i];
			}
		
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[2][i];
			}
		
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[0][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[1][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[5][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[4][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[4][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[5][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[7][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[6][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[1][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[3][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[7][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[5][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[2][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[6][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[7][i];
			}
		for(int i=0;i<3;i++) {
			vertices[k++]=cube_vertex[3][i];
			}
		
		int[] indices = {
			     0, 2, 3, 1,
			     0, 4, 6, 2,
			     0, 1, 5, 4,
			     4, 5, 7, 6,
			     1, 3, 7, 5,
			     2, 6, 7, 3
			};
		normals = new float []{
			 0.0f,  0.0f, -1.0f,
			 0.0f,  0.0f, -1.0f,		
			 0.0f,   0.0f, -1.0f,
			 0.0f,   0.0f, -1.0f,
			 
			 	0.0f, 0.0f,  1.0f,
				0.0f, 0.0f,  1.0f,
				0.0f,  0.0f,  1.0f,
				0.0f,  0.0f,  1.0f,
				
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f,  0.0f,
				-1.0f,  0.0f,  0.0f,
				-1.0f,  0.0f, 0.0f, 
				
			   	1.0f,  0.0f,   0.0f,
				 1.0f,  0.0f,  0.0f,
				 1.0f,   0.0f,  0.0f,
				 1.0f,   0.0f,   0.0f,
				 
				 0.0f, -1.0f,  0.0f,
				 0.0f, -1.0f,  0.0f,
				 0.0f, -1.0f,   0.0f,
				 0.0f, -1.0f,   0.0f,
				 
				 0.0f,  1.0f,  0.0f,
				 0.0f,  1.0f,  0.0f,
				 0.0f,  1.0f,   0.0f,
				 0.0f,  1.0f,   0.0f
				 
};
				textures = new float[]{	
							0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,	0.0f, 0.0f, 
							1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f,	1.0f, 0.0f, 
							0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,	0.0f, 0.0f,
							0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,	0.0f, 0.0f,
							1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,	1.0f, 1.0f, 
							0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,	0.0f, 0.0f
	
					};
				

		
		
	}
//	public final float cube_vertex[] = 
//		{
//		   
//		};
//	public final int indice[] = {
//			 1,3,0 , //triangle 1...
//			 0,3,2 ,
//			 5,1,4 ,
//			 4,1,0 ,
//			 7,5,6 ,
//			 6,5,4 ,
//			 3,7,2 ,
//			 2,7,6 ,
//			 5,7,1 ,
//			 1,7,3 ,
//			 6,4,2 ,
//			 2,4,0
//			};
//	public final float normal[] = {
//			-1.0f,0.0f,0.0f,  -1.0f,0.0f,0.0f,  -1.0f,0.0f,0.0f,  -1.0f,0.0f,0.0f,  -1.0f,0.0f,0.0f,  -1.0f,0.0f,0.0f, //v1  v3  v0  v0  v3  v2
//			0.0f,-1.0f,0.0f,  0.0f,-1.0f,0.0f,  0.0f,-1.0f,0.0f,  0.0f,-1.0f,0.0f,  0.0f,-1.0f,0.0f,  0.0f,-1.0f,0.0f, //v5  v1  v4  v4  v1  v0
//			1.0f,0.0f,0.0f,  1.0f,0.0f,0.0f,  1.0f,0.0f,0.0f,  1.0f,0.0f,0.0f,  1.0f,0.0f,0.0f,  1.0f,0.0f,0.0f, //v7  v5  v6  v6  v5  v4
//			0.0f,1.0f,0.0f,  0.0f,1.0f,0.0f,  0.0f,1.0f,0.0f,  0.0f,1.0f,0.0f,  0.0f,1.0f,0.0f,  0.0f,1.0f,0.0f,  //v3  v7  v2  v2  v7  v6
//			0.0f,0.0f,1.0f,  0.0f,0.0f,1.0f,  0.0f,0.0f,1.0f,  0.0f,0.0f,1.0f,  0.0f,0.0f,1.0f,  0.0f,0.0f,1.0f,  //v5  v7  v1  v1  v7  v3
//			0.0f,0.0f,-1.0f,  0.0f,0.0f,-1.0f,  0.0f,0.0f,-1.0f,  0.0f,0.0f,-1.0f,  0.0f,0.0f,-1.0f,  0.0f,0.0f,-1.0f //v6  v4  v2  v2  v4  v0
//			};
//
//	public final float a[]= {
//			  0.000059f, 1.0f-0.000004f,
//			    0.000103f, 1.0f-0.336048f,
//			    0.335973f, 1.0f-0.335903f,
//			    1.000023f, 1.0f-0.000013f,
//			    0.667979f, 1.0f-0.335851f,
//			    0.999958f, 1.0f-0.336064f,
//			    0.667979f, 1.0f-0.335851f,
//			    0.336024f, 1.0f-0.671877f,
//			    0.667969f, 1.0f-0.671889f,
//			    1.000023f, 1.0f-0.000013f,
//			    0.668104f, 1.0f-0.000013f,
//			    0.667979f, 1.0f-0.335851f,
//			    0.000059f, 1.0f-0.000004f,
//			    0.335973f, 1.0f-0.335903f,
//			    0.336098f, 1.0f-0.000071f,
//			    0.667979f, 1.0f-0.335851f,
//			    0.335973f, 1.0f-0.335903f,
//			    0.336024f, 1.0f-0.671877f,
//			    1.000004f, 1.0f-0.671847f,
//			    0.999958f, 1.0f-0.336064f,
//			    0.667979f, 1.0f-0.335851f,
//			    0.668104f, 1.0f-0.000013f,
//			    0.335973f, 1.0f-0.335903f,
//			    0.667979f, 1.0f-0.335851f,
//			    0.335973f, 1.0f-0.335903f,
//			    0.668104f, 1.0f-0.000013f,
//			    0.336098f, 1.0f-0.000071f,
//			    0.000103f, 1.0f-0.336048f,
//			    0.000004f, 1.0f-0.671870f,
//			    0.336024f, 1.0f-0.671877f,
//			    0.000103f, 1.0f-0.336048f,
//			    0.336024f, 1.0f-0.671877f,
//			    0.335973f, 1.0f-0.335903f,
//			    0.667969f, 1.0f-0.671889f,
//			    1.000004f, 1.0f-0.671847f,
//			    0.667979f, 1.0f-0.335851f
//			
//	};
	public float getlength(){
		return length;
	}
	public void setlength(float length){
		this.length = length;
		updated = false;
	}
}
