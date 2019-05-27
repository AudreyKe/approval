const delay = require('webpack-api-mocker/utils/delay');

const proxy = {
  // Priority processing.
  // apiMocker(app, path, option)
  // This is the option parameter setting for apiMocker
  // webpack-api-mocker@1.5.15 support
  'GET /snaker/surrogate/list': (req, res) => {
    return res.send({
  "code": "000",
  "message": "执行成功",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "totalCount": 5,
    "result": [
      {
        "id": "9271db432fcb4380a84ec0169778acb1",
        "processName": "project",
        "operator": "test01",
        "surrogate": "test02",
        "odate": null,
        "sdate": "2018-09-01 00:00:00",
        "edate": "2018-09-30 00:00:00",
        "state": 1,
        "proDisplayName": "立项申请",
        "operatorId": "test01",
        "surrogateId": "test02"
      },
      {
        "id": "9cf59bde0cca4d88a73875d74dba4ae7",
        "processName": "project",
        "operator": "test01",
        "surrogate": "test02",
        "odate": "2018-09-28 17:57:12",
        "sdate": "2018-09-28 17:57:00",
        "edate": "2018-09-28 17:57:00",
        "state": 1,
        "proDisplayName": "立项申请",
        "operatorId": "test01",
        "surrogateId": "test02"
      },
      {
        "id": "e4fc0d054ac64a01864fb81d9e582e94",
        "processName": "project",
        "operator": "test01",
        "surrogate": "test02",
        "odate": "2018-09-28 17:48:09",
        "sdate": "2018-09-28 17:47:00",
        "edate": "2018-09-28 17:47:00",
        "state": 1,
        "proDisplayName": "立项申请",
        "operatorId": "test01",
        "surrogateId": "test02"
      },
      {
        "id": "bfb61b13c5284a309496edb52d3767f9",
        "processName": "review",
        "operator": "test01",
        "surrogate": "test02",
        "odate": "2018-09-28 17:57:21",
        "sdate": "2018-09-28 23:57:00",
        "edate": "2018-09-28 17:57:00",
        "state": 1,
        "proDisplayName": "项目评审申请",
        "operatorId": "test01",
        "surrogateId": "test02"
      },
      {
        "id": "12b062a62dbc418eb42a3ea59f9dd924",
        "processName": "project",
        "operator": "test01",
        "surrogate": "test03",
        "odate": "2018-09-28 17:48:41",
        "sdate": "2018-09-28 17:48:00",
        "edate": "2018-09-28 17:48:00",
        "state": 1,
        "proDisplayName": "立项申请",
        "operatorId": "test01",
        "surrogateId": "test03"
      }
    ],
    "totalPages": 1,
    "hasNext": false,
    "nextPage": 1,
    "hasPre": false,
    "prePage": 1
  }
});
  },
  'POST /snaker/auth/login': (req, res) => {
    const { password, userName } = req.body;
    if (password === 'test' && userName === 'test') {
      return res.send({
        status: 'ok',
        code: 0,
        token: "sdfsdfsdfdsf",
        data: {
          id: 1,
          username: 'kenny',
          sex: 6
        }
      });
    } else {
      return res.status(403).json({
        status: 'error',
        code: 403
      });
    }
  }
}
module.exports = delay(proxy, 1000)
