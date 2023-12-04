package main

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

func main() {
	fileLines := ReadFile()

	answer := One(fileLines)
	fmt.Println(answer)
}

func One(fileLines []string) int {
	answer := 0

	for _, line := range fileLines {
		letters := strings.Split(line, "")
		var nums []int

		for _, letter := range letters {
			num, err := strconv.Atoi(letter)
			if err != nil {
				continue
			}
			nums = append(nums, num)
		}
		magicNumber := ((10 * nums[0]) + nums[len(nums)-1])
		answer += magicNumber
	}
	return answer
}

func ReadFile() []string {

	absPath, err := filepath.Abs("../../input/day1/input.txt")

	if err != nil {
		panic(err)
	}

	var lines []string

	file, err := os.Open(absPath)

	if err != nil {
		panic(err)
	}

	defer file.Close()

	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	return lines

}
