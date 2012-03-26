package org.boc.biz;

import org.boc.rule.FunctionMapper;
import org.boc.rule.VariableResolver;

/**
 * ����ԭ��
 * 1. ��parser.ELParserTokenManager�ж�����ִʷ��ָ��ؼ��֣��ɼ�������
 * 2. ��parser.ELParserConstants�ӿ��ж���ÿ���ָ��ĳ������磺
 * 		int IF1 = 82, IF2 = 83, IF3 = 84;
 * 3. �������parser.ELParserConstants.Statement()�������ж�����ָ��ʷ���
 * 		case IF1:
 *    case IF2:
 *    case IF3:
 *      expr = IfThenElseExpression();
 *      break;
 * 4. ��ʵ������javacc��������ELParser.jj�ļ�     
 *      
 * ԭ�����ְ棺parser.ELParserTokenManager
 * 0= 1=null 2=${ 3=null 4=null 5=null 6=null 7=null 8=null 9=null 10=null
 * 11=null 12=true 13=false 14=null 15=} 16=. 17=> 18=gt 19=< 20=lt 21=== 22=eq
 * 23=<= 24=le 25=>= 26=ge 27=!= 28=ne 29=( 30=) 31=, 32=: 33=[ 34=] 35=+ 36=-
 * 37=* 38=/ 39=div 40=% 41=mod 42=not 43=! 44=and 45=&& 46=or 47=|| 48=empty
 * 49=? 50=null 51=null 52=null 53=null 54=null
 * 
 * 0= 1=null 2=null 3=null 4=null 5=null 6=null 7=null 8=null 9=null 10=null
 * 11=true 12=�� 13=false 14=�� 15=null 16=. 17=> 18=gt 19=���� 20=���� 21=< 22=lt
 * 23=С�� 24=���� 25=== 26=eq 27=�� 28=<= 29=le 30=������ 31=û���� 32=>= 33=ge 34=��С��
 * 35=���� 36=!= 37=ne 38=������ 39=( 40=�� 41=) 42=�� 43=; 44=�� 45=, 46=�� 47=: 48=[
 * 49=] 50=+ 51=�� 52=�� 53=�� 54=- 55=�� 56=�� 57=�� 58=* 59=�� 60=�� 61=/ 62=div 63=��
 * 64=�� 65=% 66=mod 67=not 68=! 69=���� 70=and 71=&& 72=���� 73=or 74=|| 75=����
 * 76=empty 77=? 78== 79=���� 80=Ϊ 81=�� 82=if 83=��� 84=���� 85=then 86=��ô 87=��
 * 88=else 89=���� 90=��Ȼ 91=+= 92=���� 93=-= 94=��ȥ 95={ 96=} 97=null 98=null 99=null
 * 100=null 101=null
 * 
 * {
0-10  "", null, null, null, null, null, null, null, null, null, null, 
11-17 "\164\162\165\145", "\u771f", "\146\141\154\163\145", "\u5047", "\156\165\154\154", "\56", "\76", 
18-23  "\147\164", "\u5927\u4e8e", "\u8d85\u8fc7", "\74", "\154\164", "\u5c0f\u4e8e", 
24-31 "\u5c11\u4e8e", "\75\75", "\145\161", "\u662f", "\74\75", "\154\145", "\u4e0d\u5927\u4e8e", 
32-37 "\u6ca1\u8d85\u8fc7", "\76\75", "\147\145", "\u4e0d\u5c0f\u4e8e", "\u81f3\u5c11", "\41\75", 
38-45 "\156\145", "\u4e0d\u7b49\u4e8e", "\50", "\uff08", "\51", "\uff09", "\73", "\uff1b", 
46-55 "\54", "\uff0c", "\72", "\133", "\135", "\53", "\u52a0", "\u6b63", "\uff0b", "\55", 
56-64 "\u51cf", "\u8d1f", "\uff0d", "\52", "\u4e58", "\327", "\57", "\144\151\166", "\u9664", 
65-71 "\367", "\45", "\155\157\144", "\156\157\164", "\41", "\u4e0d\u662f", "\141\156\144", 
72-76 "\46\46", "\u5e76\u4e14", "\157\162", "\174\174", "\u6216\u8005", 
77-84 "\145\155\160\164\171", "\77", "\75", "\u7b49\u4e8e", "\u4e3a", "\uff1d", "\151\146", "\u5982\u679c", 
85-89 "\u5047\u5982", "\164\150\145\156", "\u90a3\u4e48", "\u5c31", "\145\154\163\145", 
90-96 "\u5426\u5219", "\u4e0d\u7136", "\53\75", "\u52a0\u4e0a", "\55\75", "\u51cf\u53bb", "\173", 
97- "\175", null, null, null, null, null, };
 * ԭ�����ְ棺 format.ELParserTokenManager
 * 0= 1=null 2=${ 3=null 4=null 5=null 6=null 7=null 8=null 9=null 10=null 11=null 12=true 
 * 13=false 14=null 15=} 16=. 17=> 18=gt 19=< 20=lt 21=== 22=eq 23=<= 24=le 25=>= 26=ge 
 * 27=!= 28=ne 29=( 30=) 31=, 32=: 33=[ 34=] 35=+ 36=- 37=* 38=/ 39=div 40=% 41=mod 42=not 
 * 43=! 44=and 45=&& 46=or 47=|| 48=empty 49=? 50=null 51=null 52=null 53=null 54=null 
 * 0= 1=null 2=null 3=null 4=null 5=null 6=null 7=true 8=�� 9=false 10=�� 11=null 12=. 13=> 
 * 14=gt 15=���� 16=���� 17=< 18=lt 19=С�� 20=���� 21=== 22=eq 23=�� 24=<= 25=le 26=������ 27=û���� 28=>= 
 * 29=ge 30=��С�� 31=���� 32=!= 33=ne 34=������ 35=( 36=�� 37=) 38=�� 39=; 40=�� 41=, 42=�� 43=: 44=[ 45=] 
 * 46=+ 47=�� 48=�� 49=�� 50=- 51=�� 52=�� 53=�� 54=* 55=�� 56=�� 57=/ 58=div 59=�� 60=�� 61=% 62=mod 63=not 
 * 64=! 65=���� 66=and 67=&& 68=���� 69=or 70=|| 71=���� 72=empty 73=? 74== 75=���� 76=Ϊ 77=�� 78=if 
 * 79=��� 80=���� 81=then 82=��ô 83=�� 84=else 85=���� 86=��Ȼ 87=+= 88=���� 89=-= 90=��ȥ 91={ 92=} 93=null 
 * 94=null 95=null 96=null 97=null 
 */
public interface IBiz extends VariableResolver, FunctionMapper {
	/**
	 * �����ã�����Ϊ�����ǵ���˼
	 
	public void run() {
		ExpressionEvaluatorImpl expr = new ExpressionEvaluatorImpl();
		String s1 = "100*2 + 300 + 500/1-800";
		// ��Ҫ�ո�
		String s2 = "���((Ǭ�� ��С�� 3 ���� ��� С�� 4)����(100>200 ���� 11<10))��ô(y Ϊ  \"���\"); ���� (\"���»����ã�\")��";
		String s3 = "��� Ǭ��<=300 ��ô y Ϊ ���;";
		String s4 = "��� Ǭ��<=300 ���� ��� С�� 1 ��ô �ǶԵģ� ���� \"�Ǵ�ģ���Ϊ����˵1\"��";
		String s5 = "��� Ǭ��<=300 ��ô ���;";
		try {
			String rs = (String) expr.evaluate(s5, String.class, this, this);
			System.out.println("���н��Ϊ��" + rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void outCn() {
		for (int i = 0; i < ELParserTokenManager.jjstrLiteralImages.length; i++) {
			if (ELParserTokenManager.jjstrLiteralImages[i] == null) {
				System.out.print(i + "=" + ELParserTokenManager.jjstrLiteralImages[i]+ " ");
				continue;
			}
			System.out.print(i + "=" + new String(ELParserTokenManager.jjstrLiteralImages[i]) + " ");
		}
	}
*/
	/**
	 * �����ṩ�����������Ӧ��ֵ 1.
	 * ��ÿ����������涨��ͬ�����ͣ�int/string/date�൱�ڰ���/����/���/��֧���ͣ��Ӷ�ÿ��������װ��Variable�� 2.
	 * �ɱ����������Ӧ��Variable�������û���ҵ�����ӻ���inputMap��ȡ�ñ�����inputMapû����ֱ����Ϊ�ַ������أ� 3.
	 * ����ҵ���inputMap�л�û�иñ�����Ӧ��ֵ������룻 4.
	 * �������������繫ʽ��sql���м����������ñ������м�ֵ�����outputMap�󷵻أ�
	 * ����ʶ��ģ�����ԭ�ַ�������
	 * ����ע�⣺�޸���FunctionInvocation����evaluate��174��
	 
	public Object resolveVariable(String pName) throws ELException {
		// System.out.println(pName);
		if (pName.equals("Ǭ��"))
			return 100;
		else if (pName.equals("���"))
			return 2; // 200��2
		return pName;
	}
*/
	/**
	 * ������ÿ�θ�������ֵʱ�����ᴥ���÷��������Խ����������������������outputMap�� �磺 if(x>3)
	 * y=5����y=5�ͻᴥ��������������x��ֵ���ᴥ��
	 
	public void setVariable(String name, Object value) throws ELException {
		 System.out.println("setVariable="+name+"\t"+value);
	}

	public Method resolveFunction(String prefix, String localName) {
		return null;
	}
	
*/
}
