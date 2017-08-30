import turtle

def mov_turtle(turtle, posx, posy):
    turtle.penup()
    turtle.setx(posx)
    turtle.sety(posy)
    turtle.pendown()

def draw_triangle(turtle, size):
    for i in range(0,3):
        turtle.forward(size)
        turtle.left(120)

def draw_triangle_inverse(turtle, size):
        turtle.left(60)
        draw_triangle(turtle, size)
        turtle.right(60)

def draw_inf_triangle():
    window = turtle.Screen()
    window.bgcolor("gray")

    turtle1 = turtle.Turtle()
    turtle1.shape("turtle")
    turtle1.color("purple")
    turtle1.speed(6)

    mov_turtle(turtle1, -400, -340)

    size = 800
    draw_triangle(turtle1, size)
    
    while size > 25:
        size = size /2
        print size

        mov_turtle(turtle1, -400, -340)
        turtle1.forward(size)

        step = 800/size

        #for i in range(1, step):
         #   draw_triangle_inverse(turtle1, size)
          #  turtle1.forward(2*size)
        
        if size == 400:
            draw_triangle_inverse(turtle1, size)
        elif(size == 200):
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)
            draw_triangle_inverse(turtle1, size)
        elif(size == 100):
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
        elif(size == 50):
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
        elif(size == 25):   
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
            turtle1.forward(2*size)  
            draw_triangle_inverse(turtle1, size)
        
    window.exitonclick()

draw_inf_triangle()
