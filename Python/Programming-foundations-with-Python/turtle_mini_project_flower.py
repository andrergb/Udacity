import turtle

def draw_diamond(turtle):
    turtle.forward(100)
    turtle.right(45)

    turtle.forward(100)
    turtle.right(135)

    turtle.forward(100)
    turtle.right(45)
    
    turtle.forward(100)
    turtle.right(135)

def draw_flower():
    window = turtle.Screen()
    window.bgcolor("lightblue")

    turtle1 = turtle.Turtle()
    turtle1.shape("turtle")
    turtle1.color("red")
    turtle1.speed(20)

    for i in range (0,60):
        draw_diamond(turtle1)
        turtle1.right(6)

    turtle1.right(90)
    turtle1.forward(350)

    window.exitonclick()

draw_flower()
