# Barren Land Analysis

## Problem Statement

You have a farm of 400m by 600m where coordinates of the field are from (0, 0) to (399, 599). A portion of the farm is barren, and all the barren land is in the form of rectangles. Due to these rectangles of barren land, the remaining area of fertile land is in no particular shape. An area of fertile land is defined as the largest area of land that is not covered by any of the rectangles of barren land. Read input from STDIN. Print output to STDOUT.

### Input

You are given a set of rectangles that contain the barren land. These rectangles are defined in a string, which consists of four integers separated by single spaces, with no additional spaces in the string. The first two integers are the coordinates of the bottom left corner in the given rectangle, and the last two integers are the coordinates of the top right corner. 

### Output

Output all the fertile land area in square meters, sorted from smallest area to greatest, separated by a space.

## Installation

This Java project requires Java 8+ with Maven. To build please run

```sh
mvn clean package
```

To run please use

```sh
java BLA "<BL_X BL_Y TR_X TR_Y>"
```

where BL => Bottom-left corner and TR => Top-right corner.

## Testing

Several unit test cases are included and can be run by using

```sh
mvn clean test
```

## Potential Alternative Solutions

This is a simplified problem with unique constraints because of which even though this solution is O(M*N) in complexity, it still can work reasonably well on the specific problem characteristics.

In real-life, where this can be a more complex scenario, this approach will not scale. This is more generally called the polygon clipping problem and is common in computer graphics and geographical / mapping applications. It can easily be solved with well-known specialized algorithms such as the [Vatti](https://en.wikipedia.org/wiki/Vatti_clipping_algorithm)  clipping algorithm, the [Sutherland-Hodgman](https://en.wikipedia.org/wiki/Sutherland%E2%80%93Hodgman_algorithm) algorithm and the [Weiler-Atherton](https://en.wikipedia.org/wiki/Weiler%E2%80%93Atherton_clipping_algorithm) clipping algorithm. There are excellent implemntations of these already in standard computational geometry and geo-spatial libraries libraries such as [JTS Topology Suite](https://locationtech.github.io/jts/), [CGAL](https://www.cgal.org/) and [Clipper](http://www.angusj.com/delphi/clipper.php) and we could either re-implement or integrate them into a solution if necessary to solve this in a more robust as well as more performant manner.