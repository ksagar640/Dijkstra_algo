package webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/solve")
public class algo extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
	
		int n=Integer.parseInt(request.getParameter("n"));
		int m=Integer.parseInt(request.getParameter("m"));
		ArrayList<ArrayList<Integer>> arr=new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<=n;i++)
		{
			ArrayList<Integer> temp=new ArrayList<Integer>();
			arr.add(temp);
		}
		String str[]=request.getParameter("edges").trim().split(" ");
		if(str.length!=3*m)
		{
			request.setAttribute("ans","INVALID PARAMETERS");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
		HashMap<String,Integer> map=new HashMap<>();
		for(int i=0;i<str.length;i=i+3)
		{
			int x=Integer.parseInt(str[i]);
			int y=Integer.parseInt(str[i+1]);
			int w=Integer.parseInt(str[i+2]);
			arr.get(x).add(y);
			arr.get(y).add(x);
			map.put(x+"@"+y,w);
			map.put(y+"@"+x,w);
		}
		int s=Integer.parseInt(request.getParameter("s"));
		int par[]=new int[n+1];
		for(int i=0;i<par.length;i++)
		{
			par[i]=-1;
		}
		par[s]=s;
		boolean vis[]=new boolean[n+1];
		int val[]=new int[n+1];
		Arrays.fill(val,Integer.MAX_VALUE);
		val[s]=0;
		Queue<Integer> q=new LinkedList<Integer>();
		q.add(s);
		while(!q.isEmpty())
		{
			int temp=q.remove();
			vis[temp] = true;
			for(int j=0;j<arr.get(temp).size();j++)
			{
				if(val[temp]+map.get(temp+"@"+arr.get(temp).get(j))<val[arr.get(temp).get(j)])
				{
					val[arr.get(temp).get(j)]=val[temp]+map.get(temp+"@"+arr.get(temp).get(j));
					par[arr.get(temp).get(j)]=temp;
				}
			}
			int ind=-1;int max=Integer.MAX_VALUE;
			for(int j=0;j<arr.get(temp).size();j++)
			{
				if(!vis[arr.get(temp).get(j)]&&val[arr.get(temp).get(j)]<max)
				{
					ind=arr.get(temp).get(j);
					max=val[arr.get(temp).get(j)];
				}
			}
			if(ind!=-1)
			{
				q.add(ind);
			}
		}
		
		StringBuilder ans=new StringBuilder();
		for(int i=1;i<=n;i++)
		{
			StringBuilder kk=new StringBuilder();
			int xx=i;
			
			kk.append(i+" ");
			while(true)
			{
				if(xx<0||xx==par[xx])
				{
					break;
				}
				xx=par[xx];
				kk.append(xx+" ");
				
			}
			ans.append("Node "+i+" ->");
			ans.append("Cost:"+val[i]+"  "+"Path :");
			ans.append(kk.reverse());
			ans.append("<br>");
		}
//		request.setAttribute("ans","COST: "+cost);
		request.setAttribute("ans",ans.toString());
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}
	
	

	
}