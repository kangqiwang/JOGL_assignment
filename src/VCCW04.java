import static com.jogamp.opengl.GL.GL_BGR;
import static com.jogamp.opengl.GL.GL_RGB;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_UNSIGNED_BYTE;
import static com.jogamp.opengl.GL3.*;

import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Basic.ShaderProg;
import Basic.Transform;
import Basic.Vec4;
import Objects.SObject;
import Objects.SCube;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class VCCW04 extends JFrame{

	final GLCanvas canvas; //Define a canvas 
	final FPSAnimator animator=new FPSAnimator(60, true); 
	final Renderer renderer = new Renderer();

	public VCCW04() {
        GLProfile glp = GLProfile.get(GLProfile.GL3);
        GLCapabilities caps = new GLCapabilities(glp);
        canvas = new GLCanvas(caps);

		add(canvas, java.awt.BorderLayout.CENTER);// Put the canvas in the frame
		canvas.addGLEventListener(renderer);//Set the canvas to listen GLEvents

		animator.add(canvas);

		setTitle("coursework 3_b");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		animator.start();
		canvas.requestFocus();
		}

	public static void main(String[] args) {
		new VCCW04();
	}

	class Renderer implements GLEventListener {

		private Transform T = new Transform();
		private ByteBuffer texImg;
		
		//VAOs and VBOs parameters
		private int idPoint=0, numVAOs = 2;
		private int idBuffer=0, numVBOs = 2;
		private int idElement=0, numEBOs = 2;
		private int[] VAOs = new int[numVAOs];
		private int[] VBOs = new int[numVAOs];
		private int[] EBOs = new int[numEBOs];

		//Model parameters
		private int[] numElements = new int[numEBOs];
		private long vertexSize; 
		private long normalSize; 
		private long textureSize;
		private int vPosition;
		private int vNormal;
		private int vTexCoord;

		//Transformation parameters
		private int ModelView;
		private int NormalTransform;
		private int Projection; 

		//Lighting parameter
		private int AmbientProduct;
		private int DiffuseProduct;
		private int SpecularProduct;			
		private int Shininess;
		private int Width, Height;
		

		private float[] ambient1; 
	    private float[] diffuse1;
	    private float[] specular1;
	    private float  materialShininess1;
	    
	    
		private float[] ambient2; 
	    private float[] diffuse2;
	    private float[] specular2;
	    private float  materialShininess2;
	    
		@Override
		public void display(GLAutoDrawable drawable) {
			GL3 gl = drawable.getGL().getGL3(); // Get the GL pipeline object this 
			
			gl.glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);


			T.initialize();
			T.scale(0.3f, 0.3f, 0.3f);

			gl.glUniformMatrix4fv( ModelView, 1, true, T.getTransformv(), 0 );			
			gl.glUniformMatrix4fv( NormalTransform, 1, true, T.getInvTransformTv(), 0 );			
			

			gl.glUniform4fv( AmbientProduct, 1, ambient1,0 );
		    gl.glUniform4fv( DiffuseProduct, 1, diffuse1, 0 );
		    gl.glUniform4fv( SpecularProduct, 1, specular1, 0 );			
		    gl.glUniform1f( Shininess, materialShininess1);

		    idPoint=0;
			idBuffer=0;
			idElement=0;
			bindObject(gl);
		    gl.glDrawElements(GL_TRIANGLES, numElements[0], GL_UNSIGNED_INT, 0);
		}

		
		@Override
		public void init(GLAutoDrawable drawable) {
			GL3 gl = drawable.getGL().getGL3(); // Get the GL pipeline object this 
			
			
			 try {
				 texImg = readImage("resource\\PnWelshDragon.jpg");
			    } catch(Exception ex) {
		            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			    	
			    }
			
			gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, Width, Height, 0, GL_BGR, GL_UNSIGNED_BYTE, texImg);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); 

			
			

			ShaderProg shaderproc = new ShaderProg(gl, "resource//Gouraud.vert", "resource//Gouraud.frag");
			int program = shaderproc.getProgram();
			gl.glUseProgram(program);


		    gl.glGenVertexArrays(numVBOs,VAOs,0);
			gl.glGenBuffers(numVBOs, VBOs,0);
			gl.glGenBuffers(numEBOs, EBOs,0);

		    float[] lightPosition = {10.0f, 10.0f, 10.0f, 1.0f};
		    Vec4 lightAmbient = new Vec4(0.7f, 0.7f, 0.7f, 0.7f);
		    Vec4 lightDiffuse = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		    Vec4 lightSpecular = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);

		    gl.glUniform4fv( gl.glGetUniformLocation(program, "LightPosition"),
				  1, lightPosition, 0 );

		    SObject cube = new SCube();
		    idPoint=0;
			idBuffer=0;
			idElement=0;
			createObject(gl, cube);
		    Vec4 materialAmbient = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		    Vec4 materialDiffuse = new Vec4(0.7f, 0.7f, 0.7f, 1.0f);
		    Vec4 materialSpecular = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		    materialShininess1 = 64.0f;
			
			Vec4 ambientProduct = lightAmbient.times(materialAmbient);
		    ambient1 = ambientProduct.getVector();
		    Vec4 diffuseProduct = lightDiffuse.times(materialDiffuse);
		    diffuse1 = diffuseProduct.getVector();
		    Vec4 specularProduct = lightSpecular.times(materialSpecular);
		    specular1 = specularProduct.getVector();
			
		   
		    // This is necessary. Otherwise, the The color on back face may display 
//		    gl.glDepthFunc(GL_LESS);
		    gl.glEnable(GL_DEPTH_TEST);		    
		}
		
	
		@Override
		public void dispose(GLAutoDrawable drawable) {
			// TODO Auto-generated method stub
			
		}
		
		public void createObject(GL3 gl, SObject obj) {
			float [] vertexArray = obj.getVertices();
			float [] normalArray = obj.getNormals();
			float [] textureArray = obj.getTextures();
			int [] vertexIndexs =obj.getIndices();
			numElements[idElement] = obj.getNumIndices();

			bindObject(gl);
			
			FloatBuffer vertices = FloatBuffer.wrap(vertexArray);
			FloatBuffer normals = FloatBuffer.wrap(normalArray);
			FloatBuffer textures = FloatBuffer.wrap(textureArray);

		    // Create an empty buffer with the size we need 
			// and a null pointer for the data values

			vertexSize = vertexArray.length*(Float.SIZE/8);
			normalSize = normalArray.length*(Float.SIZE/8);
			textureSize = textureArray.length*(Float.SIZE/8);
			gl.glBufferData(GL_ARRAY_BUFFER, vertexSize +normalSize +textureSize, 
					null, GL_STATIC_DRAW);
		    
			// Load the real data separately.  We put the colors right after the vertex coordinates,
		    // so, the offset for colors is the size of vertices in bytes
		    gl.glBufferSubData( GL_ARRAY_BUFFER, 0, vertexSize, vertices );
		    gl.glBufferSubData( GL_ARRAY_BUFFER, vertexSize, normalSize, normals );
		    gl.glBufferSubData( GL_ARRAY_BUFFER, vertexSize + normalSize, textureSize, textures );
		    
			IntBuffer elements = IntBuffer.wrap(vertexIndexs);			

			long indexSize = vertexIndexs.length*(Integer.SIZE/8);
			gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexSize, elements, 
					GL_STATIC_DRAW); // pay attention to *Float.SIZE/8						
			gl.glEnableVertexAttribArray(vPosition);
			gl.glVertexAttribPointer(vPosition, 3, GL_FLOAT, false, 0, 0L);
			gl.glEnableVertexAttribArray(vNormal);
		    gl.glVertexAttribPointer(vNormal, 3, GL_FLOAT, false, 0, vertexSize);
		    gl.glEnableVertexAttribArray(vTexCoord);
		    gl.glVertexAttribPointer(vTexCoord, 2, GL_FLOAT, false, 0, vertexSize + normalSize);
		}

		public void bindObject(GL3 gl){
			gl.glBindVertexArray(VAOs[idPoint]);
			gl.glBindBuffer(GL_ARRAY_BUFFER, VBOs[idBuffer]);
			gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBOs[idElement]);			
		};
		
	 private ByteBuffer readImage(String filename) throws IOException {

		        ByteBuffer imgbuf;
		        BufferedImage img = ImageIO.read(new FileInputStream(filename));

		        Width = img.getWidth();
		        Height = img.getHeight();
		        DataBufferByte datbuf = (DataBufferByte) img.getData().getDataBuffer();
		        imgbuf = ByteBuffer.wrap(datbuf.getData());
		        return imgbuf;
		    }


	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
		
	}
}

