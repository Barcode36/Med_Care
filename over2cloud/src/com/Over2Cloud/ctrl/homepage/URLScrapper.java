package com.Over2Cloud.ctrl.homepage;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opensymphony.xwork2.ActionSupport;

public class URLScrapper extends ActionSupport{
	
	private InputStream inputStream;
	private String URL="https://www.youtube.com/watch?v=BUDcZF08Kcw";
	
	
	public String getURLDetails()
	{
		StringBuilder data=new StringBuilder("");
		try
		{
			Document doc = null;
			try
			{
				doc = Jsoup.connect(getURL()).get();
			}
			catch(HttpStatusException e )
			{
				
			}
			catch(Exception he)
			{
				
			}
			
			if(doc!=null)
			{
				String title=doc.title();
				if(title!=null)
				{
					data.append(title+"#$#>");
				}
				else
				{
					data.append("No information available#$#>");
				}
				
				try
				{
					String desc = doc.select("meta[name=description]").first().attr("content");
					if(desc!=null && !desc.equalsIgnoreCase(""))
					{
						data.append(desc+"#$#>");
					}
					else
					{
						data.append("No desciption available#$#>");
					}
				}
				catch(Exception e)
				{
					data.append("No desciption available#$#>");
				}
				Elements media = doc.select("[src]");
				if(media!=null)
				{
					boolean done=true;
					for (Element src : media) {
			            if (src.tagName().equals("img"))
			            {
			            	//System.out.println(src.attr("abs:src"));
			            	if(src.attr("abs:src").contains(";"))
			    			{
			    				String temp[]=src.attr("abs:src").split(";");
			    				if(temp[0].endsWith(".png") || temp[0].endsWith(".jpg") || temp[0].endsWith(".jpeg")
				            			|| temp[0].endsWith(".cms"))
				            	{
				            		data.append(src.attr("abs:src"));
				            		done=false;
				            		break;
				            	}
			    			}
			            	else if(src.attr("abs:src").contains("?") && src.attr("abs:src").contains(".jpg")
			            			 || src.attr("abs:src").contains(".png") || src.attr("abs:src").contains(".jpeg") 
			            			 || src.attr("abs:src").contains(".cms"))
			    			{
				            		data.append(src.attr("abs:src"));
				            		done=false;
				            		break;
			    			}
			    			else
			    			{
				            	if(src.attr("abs:src").endsWith(".png") || src.attr("abs:src").endsWith(".jpg") || src.attr("abs:src").endsWith(".jpeg")
				            			|| src.attr("abs:src").endsWith(".cms"))
				            	{
				            		//System.out.println( src.attr("abs:src") +"Width :"+src.attr("width") +"  Height :"+src.attr("height"));
				            		data.append(src.attr("abs:src"));
				            		done=false;
				            		break;
				            	}
			    			}
			            }
			        }
					if(done)//no image is readed
					{
						for (Element src : media) {
				            if (src.tagName().equals("img"))
				            {
					            		data.append(src.attr("abs:src"));
					            		break;
				            }
						}
					}
				}
				else
				{
					data.append("images/No_image.png");
				}
				//System.out.println(data.toString());
			}
			inputStream = new StringBufferInputStream(data.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.append("No information available#$#>No information available#$#>images/No_image.png");
			inputStream = new StringBufferInputStream(data.toString());
		}
		return SUCCESS;
	}
	
	public static void main(String[] args) {
		
		new URLScrapper().getURLDetails();
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String url) {
		URL = url;
	}
	
}
