package com.web.hackercode.structures;

public class TestData {

  /*
	"1": {
		"answer": undefined
		"answered": false
		"status": "normal"
	}
	 * */
  private String answer;
  private Boolean isAnswered;
  private String status;

  public String getAnswer() {
    return answer;
  }

  public Boolean getIsAnswered() {
    return isAnswered;
  }

  public String getStatus() {
    return status;
  }

  public void setAnswer(String a) {
    this.answer = a;
  }

  public void setIsAnswered(Boolean a) {
    this.isAnswered = a;
  }

  public void setStatus(String a) {
    this.status = a;
  }

  public String toString() {
    return "Answer : " + getAnswer() + " status: " + getStatus();
  }
}
//class TestRecieveDataKeys{
//	private TestReceiveFormat q;
//	public String toString() {
//		return "Answer : "+q.getAnswer()+" status: "+q.getStatus();
//	}
//}
//public class TestData {
//	private ArrayList<TestRecieveDataKeys> testContent = new ArrayList<>();
//	public String toString() {
//		System.out.println("TO STRING METHOND CALLED");
//		System.out.println("LENGTH OF THE DATA RECEIVED" + testContent.size());
//		String result = "";
//		for(TestRecieveDataKeys obj : testContent) {
//			result = result + obj.toString();
//		}
//		return result;
//	}
//
//}
