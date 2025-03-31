package com.practice.game.jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.concurrent.Executors;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {

        try {
            System.out.println("Hello LWJGL " + Version.getVersion() + "!");

            init();
            loop();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public  void init() {
        //GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            throw  new IllegalStateException("Unable to initialize GLFW");
        }
        // configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, 0L, 0L);
        if (glfwWindow == 0L) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }
        // make the openGL context current
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync
        glfwSwapInterval(1);

        // make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }

        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
    }
}
