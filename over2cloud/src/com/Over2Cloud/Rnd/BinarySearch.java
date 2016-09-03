package com.Over2Cloud.Rnd;
class BinarySearch
{
    // Returns index of x if it is present in arr[], else
    // return -1
    int binarySearch(int arr[], int x)
    {
        int l = 0, r = arr.length - 1;
        while (l <= r)
        {
            int m = l + (r-l)/2;
 
            // Check if x is present at mid
            if (arr[m] == x)
                return m;
 
            // If x greater, ignore left half
            if (arr[m] < x)
                l = m + 1;
 
            // If x is smaller, ignore right half
            else
                r = m - 1;
        }
 
        // if we reach here, then element was not present
        return -1;
    }
 
    // Driver method to test above
    public static void main(String args[])
    {
        BinarySearch ob = new BinarySearch();
        int arr[] = {2, 10, 4, 3, 40};
        int n = arr.length;
        int x = 10;
        int result = ob.binarySearch(arr, x, n-1, 0);
        if (result == -1)
            System.out.println("Element not present");
        else
            System.out.println("Element found at index "+result);
    }

	private int binarySearch(int[] arr, int x, int l, int r)
	{
		// TODO Auto-generated method stub
		if(l >= r){
			int mid = r + l - r / 2 ;
			if (arr[mid]==x){
				return mid ;
			}
			if (arr[mid]< x) {
				binarySearch(arr, x, mid -1, )
			}
		}
		return 0;
	}
}