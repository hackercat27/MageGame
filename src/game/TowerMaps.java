package game;

public class TowerMaps {
    static final char[] TOWER1 = {
        //   0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//0
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//20
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//40
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//60
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//80
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', ' ', '+', '+', ' ', ' ', ' ', '\n',//100
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', '\n',//120
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', ' ', '[', ' ', '|', ' ', ' ', '\n',//140
            ' ', '+', '+', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', '\n',//160
            ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '+', '+', ' ', '\n',//180
            ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//200
            ' ', 'S', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '|', ' ', '\n',//220
            ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//240
            ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '+', '+', ' ', '\n',//260
            ' ', '+', '+', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', '\n',//280
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', ' ', '[', ' ', '|', ' ', ' ', '\n',//300
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', '\n',//320
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', ' ', '+', '+', ' ', ' ', ' ', '\n',//340
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//360
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', 'S', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//380
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//400
            'T'
			};
	static final int[] TOWER1_METADATA = {
		389, 5, 269,
		221, 25, 236,
		0, 0, 0,
		0, 0, 0
	};

    static final char[] TOWER2 = {
        //   0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//0
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//20
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//40
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', 'S', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//60
            ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//80
            ' ', ' ', ' ', ' ', ' ', '+', ' ', '[', ' ', ' ', ' ', ' ', '|', ' ', '+', '+', ' ', ' ', ' ', '\n',//100
            ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '+', '+', ' ', ' ', '\n',//120
            ' ', ' ', '+', '+', '+', ' ', '+', '+', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', '|', ' ', ' ', '\n',//140
            ' ', '+', '+', ' ', '+', '+', ' ', '+', '+', ' ', ' ', ' ', '|', ' ', ' ', ' ', '+', '+', ' ', '\n',//160
            ' ', '|', ' ', ' ', ' ', '+', '+', ' ', '+', '+', ' ', ' ', '|', ' ', ' ', ' ', ' ', '|', ' ', '\n',//180
            ' ', '|', ' ', '#', ' ', ' ', '+', '+', ' ', '+', '=', '=', '+', ' ', ' ', ' ', ' ', '|', ' ', '\n',//200
            ' ', '|', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', 'S', ' ', '\n',//220
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', '|', ' ', '\n',//240
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//260
            ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', '+', '+', '+', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', '\n',//280
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '+', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '\n',//300
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', '\n',//320
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', '\n',//340
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//360
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//380
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//400
            'T'
			};

	static final int[] TOWER2_METADATA = {
		237, 24, 222,
		69, 26, 369,
		0, 0, 0,
		0, 0, 0
	};

    static final char[] TOWER3 = {
        //   0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//0
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//20
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//40
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '+', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//60
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', '|', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//80
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', '\n',//100
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', '\n',//120
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '\n',//140
            ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', '\n',//160
            ' ', '|', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', '=', '+', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//180
            ' ', '|', ' ', ' ', ' ', ' ', ' ', '+', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//200
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '|', ' ', ' ', ' ', ' ', ' ', 'S', ' ', '\n',//220
            ' ', '|', ' ', ' ', ' ', ' ', ' ', '+', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//240
            ' ', '|', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '+', '=', '=', '=', '=', '=', '+', ' ', '\n',//260
            ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//280
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//300
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//320
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//340
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//360
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', 'S', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//380
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//400
            'T'
			};
			
	static final int[] TOWER3_METADATA = {
		389, 25, 89,
		237, 27, 222,
		0, 0, 0,
		0, 0, 0
	};

    static final char[] SORCERER_ROOM = {
        //   0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//0
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//20
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//40
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//60
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//80
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', '\n',//100
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', '\n',//120
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '\n',//140
            ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', '\n',//160
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//180
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//200
            ' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//220
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//240
            ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', '\n',//260
            ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', '\n',//280
            ' ', ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|', ' ', ' ', '\n',//300
            ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', '\n',//320
            ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '+', '+', ' ', ' ', ' ', '\n',//340
            ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', ' ', '+', '=', '+', ' ', ' ', ' ', ' ', '\n',//360
            ' ', ' ', ' ', ' ', ' ', ' ', '+', '=', '=', '=', '=', '=', '+', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//380
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\n',//400
            'T'
			};
			
	static final int[] SORCERER_ROOM_METADATA = {
		221, 26, 236,
		0, 0, 0,
		0, 0, 0,
		0, 0, 0
	};

}
