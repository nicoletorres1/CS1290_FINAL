/*
* Nicole Torres
* CS1290 Final
* 05/18/19
 */



public class CS1290Final {

    /*
     * 3. Palindromic Substrings
     * Given a string we have to count how many palindromes are in the given string.
     * it is read the same from start to the end and vice versa.
     * A single letter is a palindrome itself.
     * Dynamic programming bottom up technique is used to solve this problem
     * If we know that the inner word of the string is a palindrome then all we have to do is check
     * the strings beginning and end.
     */

    public int countSubstrings(String s) {
        int n = s.length();
        int res = 0;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]);
                if(dp[i][j]) ++res;
            }
        }
        return res;
    }


    /*
     * 7. Integer Break
     * ar to the knapsack problem and Perfect squares problem
     * The first thing we should consider is : What is the max product if we break a number N into two factors?
     * I use a function to express this product: f=x(N-x)
     * When x=N/2, we get the maximum of this function.
     * However, factors should be integers. Thus the maximum is (N/2)*(N/2) when N is even or (N-1)/2 *(N+1)/2 when N is odd.
     * When the maximum of f is larger than N, we should do the break.(N/2)*(N/2)>=N, then N>=4 (N-1)/2 *(N+1)/2>=N, then N>=5
     */

    int[] dp;

    public int helper(int n){
        //base
        if(n <= 0)
            return 1;

        //memoization
        if(dp[n] > 0)
            return dp[n];

        // recursive step to check all possible solutions
        int max = 0;
        for(int i=1;i<n;i++){
            int val = Math.max((n-i)*i,Math.max((n-i)*helper(i),Math.max(helper(n-i)*i,helper(n-i)*helper(i))));
            if(val > max)
                max = val;
        }

        dp[n] = max;
        return max;
    }

    public int integerBreak(int n) {

        if(n == 0)
            return 1;

        dp = new int[n+1];
        dp[1] = 1;
        dp[0] = 0;
        return helper(n);
    }

    /*
     * 4. Arithmetic Slices
     * A problem is an Arithmetic sequence if it consists of at least three elements and the difference between
     * two consecutive elements is the same.
     * We find the sub problem:
        Assume A[i:j] (both include A[i] and A[j]) is an arithmetic slice, then we have:
        if A[i]-A[i-1] = = A[i+1]-A[i], then A[i-1:j] is an arithmetic slice;
        if A[j+1]-A[j] = = A[j]-A[j-1], then A[i:j+1] is an arithmetic slice.
        use dp[i][j] to memorize whether A[i:j] is an arithmetic slice, and count to count the num of arithmetic slices:
     */

    public int numberOfArithmeticSlices(int[] A) {
        int n=A.length;
        if(n<3){
            return 0;
        }
        int[] dp=new int[n];
        dp[0]=0;
        dp[1]=0;
        int sum=0;
        for(int i=2;i<n;i++){
            if((A[i]-A[i-1])==(A[i-1]-A[i-2])){
                dp[i]=dp[i-1]+1;
            }else{
                dp[i]=0;
            }
            sum+=dp[i];
        }
        return sum;
    }


    /*
    * 9. Perfect SquaresFirst of all, we created the DP array as usual.
    * This DP array stands for the least number of perfect square numbers for its index.
    * For example DP[13]=2 stands for if you want to decompose 13 into some perfect square numbers,
    * it will contains at least two terms which are 33 and 22.
    * After the initialization of the DP array. We want to iterate through the array to fill all indices.
    * During each iteration we're actually doing this: dp[i] = 1 + min (dp[i-j*j] for j*j<=i)
     */

    public int NumSquares(int n) {
        int[] DP = new int[n + 1];
        for (int i = 1; i <= n; i++)
        {
            int min= int.MaxValue;
            for (int j = 1; j * j <= i; j++)
            {
                min= Math.min(min, DP[i - j * j] + 1);
            }
            DP[i] = min;
        }
        return DP[n];
    }

    /*
     * 8. Partition to K Equal Sum Subsets
     * Assume sum is the sum of nums[] . The dfs process is to find a subset of nums[] which sum equals to sum/k.
     * We use an array visited[] to record which element in nums[] is used. Each time when we get a cur_sum = sum/k,
     * we will start from position 0 in nums[] to look up the elements that are
     * not used yet and find another cur_sum = sum/k.
    * An corner case is when sum = 0, my method is to use cur_num to record the number of elements
    * in the current subset.
    * Only if cur_sum = sum/k && cur_num >0, we can start another look up process.
     */


    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums)
            sum += num;
        if(k <= 0 || sum % k != 0)
            return false;

        return canPartition(nums, 0, 0, k, 0, sum/k);
    }

    public boolean canPartition(int[] nums, int used, int start_index, int k, int cur_sum, int target){
        if (k == 1)
            return true; // this means find 1 subset whose sum is sum/k.  This is rather obvious, because sum - (k-1)* target ...
        if (cur_sum > target)
            return false;

        for(int i = start_index; i < nums.length; i++){
            if ((used & (1 << i)) != 0) continue;

            int new_used = (used | (1 << i));
            if ((cur_sum + nums[i])== target && canPartition(nums, new_used, 0, k-1, 0, target))
                return true;

            if (canPartition(nums, new_used, i+1, k, cur_sum + nums[i], target))
                return true;
        }
        return false;
    }

}
