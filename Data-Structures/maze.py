from passable import passable

class Maze:
    def __init__(self):
        self.maze = dict()
        self.lines = []
        self.numbers = [list() for x in range(10)]
        self.solution = []
        self.layer = 0

    def input(self):
        num_lines = int(input("Enter column length: "))
        length_line = int(input("Enter row length: "))
        line = ""
        for i in range(num_lines):
            while len(line) != length_line:
                line = input("Enter line: ")

            self.lines.append(line)
            max_len_line = len(line)
            line = ""

    def setup(self):
        column_range = range(len(self.lines))
        row_range = range(len(self.lines[0]))
        for i in column_range:
            for j in row_range:
                character = self.lines[i][j]
                if character in passable:
                    self.maze.update({(i, j): list()})
                    if ((i - 1) in column_range) and (self.lines[i - 1][j] in passable):
                        self.maze[(i, j)].append((i - 1, j))
                    if ((i + 1) in column_range) and (self.lines[i + 1][j] in passable):
                        self.maze[(i, j)].append((i + 1, j))
                    if ((j - 1) in row_range) and (self.lines[i][j - 1] in passable):
                        self.maze[(i, j)].append((i, j - 1))
                    if ((j + 1) in row_range) and (self.lines[i][j + 1] in passable):
                        self.maze[(i, j)].append((i, j + 1))

                    if character.isnumeric():
                        number = int(character)
                        self.numbers[number].append((i, j))
                    elif character == '@':
                        self.start = (i, j)
                    elif character == '$':
                        self.target = (i, j)

        for number in self.numbers:
            for i in range(len(number)):
                for j in range(i + 1, len(number)):
                    self.maze[number[i]].append(number[j])
                    self.maze[number[j]].append(number[i])

    def solve(self):
        to_visit = [self.start]
        visited = set(to_visit)
        next_layer = []
        connections = {}
        
        while self.target not in to_visit:

            for i in range(len(to_visit)):
                tile = to_visit.pop(0)
                tiles = self.maze.get(tile)
                nexts = list(filter(lambda x: x not in visited, tiles))
                for next in nexts:
                    if next not in connections:
                        connections.update({next: tile})
                next_layer.extend(nexts)
        
            to_visit.extend(next_layer)
            visited.update(to_visit)
        
            if len(to_visit) == 0:
                self.layer = -1
                break
        
            next_layer = []
            self.layer += 1
        
        if self.layer > 0:
            tile = self.target
            self.solution.append(tile)
            while tile is not self.start:
                tile = connections.get(tile)
                self.solution.append(tile)
            self.solution.reverse()

    def present(self):
        if self.layer < 0:
            print("Unsolvable Maze")
        else:
            presentation = self.lines.copy()
            for tile in self.solution:
                i, j = tile
                presentation[i] = presentation[i][:j] + '!' + presentation[i][j + 1:]
            print("Solved in " + str(self.layer) + " steps.")
            print("Solution coordinates:", self.solution)
            print("Unsolved Maze")
            for line in self.lines:
                print(line)
            print("Solved Maze")
            for line in presentation:
                print(line)
