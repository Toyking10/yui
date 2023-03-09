package yui

import (
	"image"
	"image/color"
)

type RGB struct {
	r uint8
	g uint8
	b uint8
}

func TranslateToRGB(img image.Image) []RGB {
	return (colorArrayToRGB(toColorArray(img)))
}

func toColorArray(img image.Image) []color.Color {
	width := img.Bounds().Max.X
	height := img.Bounds().Max.Y
	var output []color.Color
	for y := 0; y < height; y++ {
		for x := 0; x < width; x++ {
			output = append(output, img.At(x, y))
		}
	}
	return output
}

func colorArrayToRGB(input []color.Color) []RGB {
	var output []RGB
	for _, c := range input {
		r, g, b, _ := c.RGBA()
		r8 := uint8(r)
		g8 := uint8(g)
		b8 := uint8(b)
		output = append(output, RGB{r8, g8, b8})
	}
	return output
}
