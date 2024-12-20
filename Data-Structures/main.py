from maze import Maze

mazes = []
amount = int(input("Enter number of mazes: "))
for i in range(amount):
    mazes.append(Maze())
    maze = mazes[i]
    maze.input()
    maze.setup()
    maze.solve()
    maze.present()
