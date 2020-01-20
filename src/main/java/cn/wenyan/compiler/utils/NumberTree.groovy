package cn.wenyan.compiler.utils;


class NumberTree {

    private Node root = new Node()

    private BigDecimal floatValue = 0

    private List<Node> nodes = new ArrayList<>()

    void inputNumber(String wenyan,Node node){
        node.value = wenyan
        int index = GroovyUtils.getMax(wenyan)
        nodes.remove(node.parent)
        nodes.add(node)
        if(wenyan.size() == 1)return
        if(index == -1)return
        String left = wenyan.substring(0,index)
        String right = wenyan.substring(index+1)
        String max = wenyan.charAt(index)
        node.left = new Node()
        node.right = new Node()
        node.middle = new Node()
        node.left.value = left
        node.left.parent = node
        node.middle.value = max
        node.middle.parent = node
        node.right.value = right
        node.right.parent = node
        inputNumber(left,node.left)
        inputNumber(max,node.middle)
        inputNumber(right,node.right)
    }

    NumberTree inputNumber(String wenyan){
        if(wenyan.contains("又")){
            def numbers = wenyan.split("又")
            inputNumber(numbers[0],root)
            floatValue = GroovyUtils.getNumber(numbers[1])
        }else{
            inputNumber(wenyan,root)
        }
        this
    }

    BigDecimal convertToNumber(){
        BigDecimal result = 0
        for(int i = 0;i<nodes.size();i+=2){
            result += GroovyUtils.getNumber(nodes[i].value) * GroovyUtils.getNumber(i+1<nodes.size()?nodes[i+1].value:"一")
        }
        result + floatValue
    }


}
class Node{

    static int id = 0

    Node parent

    String value

    Node left

    Node middle

    Node right

    int index = id++
    @Override
    String toString() {
        return value
    }
}
