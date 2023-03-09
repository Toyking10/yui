package yui

import (
	"fmt"
	"image"
)

func Encode(imgIn image.Image) {
	img := TranslateToRGB(imgIn)
	for _, c := range img {
		fmt.Println(c)
	}
}
