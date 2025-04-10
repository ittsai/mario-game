package com.practice.game.jade;

import com.practice.game.renderer.Shader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene{

    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertextArray = {
            // position     // color
            0.5f,  -0.5f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
            -0.5f, 0.5f, 0.0f,  0.0f, 1.0f, 0.0f, 1.0f, // Top left
            0.5f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f, 1.0f, // Top right
            -0.5f, -0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f // Bottom left
    };

    // IMPORTANT must be in counter-clockwise order
    private int[] elementArray = {
        2, 1, 0, // top right triangle
        0, 1, 3 // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;

    public LevelEditorScene() {
    }

    @Override
    public void init() {
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        // generate VAO, VBO, and EBO buffer objects, adn send to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertextArray.length);
        vertexBuffer.put(vertextArray).flip();

        // create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // add the vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }
    @Override
    public void update(float dt) {
        // bind shader program
        defaultShader.use();
        // bind the vao that we are using
        glBindVertexArray(vaoID);
        // enable the vertex attribute pointer
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        defaultShader.detach();
    }
}
