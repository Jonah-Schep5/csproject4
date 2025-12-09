/**
 * Interface for Bintree nodes.
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
interface BintreeNode {
    BintreeNode insert(
        AirObject obj,
        int x,
        int y,
        int z,
        int xw,
        int yw,
        int zw,
        int depth,
        int dim);


    void print(
        
        int x,
        int y,
        int z,
        int xw,
        int yw,
        int zw,
        int depth,
        StringBuilder sb,
        int[] cnt);


    void findCollisions(
        int x,
        int y,
        int z,
        int xw,
        int yw,
        int zw,
        int depth,
        StringBuilder sb);


    void findIntersections(
        int x,
        int y,
        int z,
        int xw,
        int yw,
        int zw,
        int bx,
        int by,
        int bz,
        int bxw,
        int byw,
        int bzw,
        int depth,
        StringBuilder sb,
        int[] cnt);
}
