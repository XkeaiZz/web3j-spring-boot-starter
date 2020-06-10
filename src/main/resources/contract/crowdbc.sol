pragma solidity ^0.4.4;

interface Token {
    function transfer2(address recipient, uint amount) public view returns(address);
}

contract BaseContract {

    // Status for worker
    enum Status {
        Pending, // 0 等待......
        Unclaimed, // 1 未领取完
        Claimed, // 2 已领取完
        Evaluating, // 3 评估中
        Completed // 4 已完成
    }
    // Task struct
    struct Task {
        string desc; // 描述
        uint reward; // 奖励
        uint deposit; // 押金
        uint deadline; // 截止时间
        uint maxNum; // 工作者上限数量
        uint nowNum; // 工作者当前数量
        uint minReputation; // 最小信誉
        string ttype; // 任务类型
        Status status; // 任务状态
        string pointer; // 附件指针
    }

    address internal owner;
    // 修饰器-创建者独享权限
    modifier onlyOwner(){
        require(msg.sender == owner, "only owner is allowed to operate.");
        _;
    }
    modifier onlyHolder(address _addr){
        require(msg.sender == _addr, "only holder is allowed to operate.");
        _;
    }
    modifier onlyOwnerAndHolder(address _addr){
        require(msg.sender == owner || msg.sender == _addr, "only owner and holder is allowed to operate.");
        _;
    }
    // 修饰器- m > n
    modifier gt(uint m, uint n, string hintMsg){
        require(m > n, hintMsg);
        _;
    }
}
// 总合约、内含注册及管理相关
contract RegisterAndManager is BaseContract, Token {

    event RegisterEvent(address _addr, string _userName, uint registerTime);

    function transfer2(address recipient, uint amount) public view returns(address){
        return recipient;
    }

    uint maxRegistrants = 10000000;
    // 注册总人数
    uint numRegistrants = 0;
    // 用户总信誉
    uint allReputation = 0;
    uint allRWRC = 0;
    //存储数据数组
    address[] userAddrArray;
    address[] uscAddrArray;
    address[] rwrcAddrArray;

    //映射
    mapping(string => address) userNameAddrMapping;
    mapping(address => address) userUscAddrMapping;
    mapping(address => address) userRwrcAddrMapping;

    constructor() public{
        owner = msg.sender;
    }
    function isRegistered(address _addr, string _userName) public view returns (bool){
        return !((userNameAddrMapping[_userName] == address(0)) || (userUscAddrMapping[_addr] == address(0)));
    }

    function register(address _addr, string _userName, string _profile) public payable onlyOwner gt(maxRegistrants, numRegistrants, "register limtation") {
        require(isRegistered(_addr, _userName) == false, "already register");
        UscSummary uscSummary = new UscSummary(_addr, _userName, _profile, now, 60);

        userNameAddrMapping[_userName] = _addr;
        userUscAddrMapping[_addr] = uscSummary;

        userAddrArray.push(_addr);

        numRegistrants ++;
        allReputation += 60;

        emit RegisterEvent(_addr, _userName, now);
    }

    function() public payable {

    }

    function getUscAddress(address _addr) public view onlyOwnerAndHolder(_addr) returns (address){
        return userUscAddrMapping[_addr];
    }

    //    function getUscAddress(string _userName) public view returns (address){
    //        return getUscAddress(userNameAddrMapping[_userName]);
    //    }

    function getGMAddress() public view returns (address){
        return owner;
    }

    function getAllReputation() public view returns (uint){
        return allReputation;
    }

    function getUserReputation(address _addr) public view returns (uint){
        return UscSummary(userUscAddrMapping[_addr]).getReputation();
    }

    function isRelated(address _addr, string userName) public view returns (bool){
        return userNameAddrMapping[userName] == _addr;
    }
    // 更新 reputation 是在这这里好 还是 uscSummary 中
    function updateUserReputation(address _addr, uint _rep) public {
        require(userUscAddrMapping[_addr] != address(0), "account not exists!");
        require(_rep >= 0 && _rep <= 100, "reputation limitation");
        UscSummary usc = UscSummary(userUscAddrMapping[_addr]);
        allReputation += (_rep - usc.getReputation());
        usc.updateReputation(_rep);
    }

}

contract UscSummary is BaseContract {
    // 用户信息结构体
    struct User {
        address addr; // User地址
        //        address uscAddr; // USC地址
        string userName; // 用户名
        string profile; // 简介
        uint registerTime; // 注册时间
        //        uint processingTaskNum; // 进行中的任务数量
        //        uint finishedTaskNum; // 已完成的任务数量
        uint reputation; // 信誉

    }

    Task[] processingTask;
    Task[] finishedTask;
    User user;
    constructor(address _addr, string _userName, string _profile, uint _registerTime, uint _reputation) public{
        owner = msg.sender;
        user.userName = _userName;
        user.addr = _addr;
        user.profile = _profile;
        user.registerTime = _registerTime;
        user.reputation = _reputation;
    }
    mapping(address => User) addrUserMapping;

    function getReputation() public view returns (uint){
        return user.reputation;
    }

    function updateReputation(uint _rep) public onlyOwner {
        user.reputation = _rep;
    }
}

contract RWRC is BaseContract {
    string[] taskTypeArray;
    mapping(address => Task[]) processingTask;
    mapping(address => Task[]) finishedTask;
    constructor() public{
        owner = msg.sender;
    }
    function ownerWho() public view returns (address){
        return owner;
    }
}