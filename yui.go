package main

import (
	"image"
	_ "image/jpeg"
	_ "image/png"
	"os"
	yui "yui/encode"
)

func main() {
	yui.Encode(getImage("yui.png"))
}

func getImage(filePath string) image.Image {
	imageFile, err := os.Open(filePath)
	if err != nil {
		panic(err.Error())
	}
	img, _, err := image.Decode(imageFile)
	if err != nil {
		panic(err.Error())
	}
	return img
}
