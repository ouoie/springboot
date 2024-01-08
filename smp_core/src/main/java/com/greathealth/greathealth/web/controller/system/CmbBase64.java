package com.greathealth.greathealth.web.controller.system;

import java.util.Arrays;

public class CmbBase64 {
	 private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	 private static final int[] IA = new int[256];

	/**
	 *
	 */
	public CmbBase64() {
		super();
		// TODO Auto-generated constructor stub
	}


	    public static final char[] encodeToChar(byte[] sArr, boolean lineSep) {
	        int sLen = sArr != null ? sArr.length : 0;
	        if (sLen == 0) {
	            return new char[0];
	        } else {
	            int eLen = sLen / 3 * 3;
	            int cCnt = (sLen - 1) / 3 + 1 << 2;
	            int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0);
	            char[] dArr = new char[dLen];
	            int left = 0;
	            int d = 0;
	            int cc = 0;

	            while(left < eLen) {
	                int i = (sArr[left++] & 255) << 16 | (sArr[left++] & 255) << 8 | sArr[left++] & 255;
	                dArr[d++] = CA[i >>> 18 & 63];
	                dArr[d++] = CA[i >>> 12 & 63];
	                dArr[d++] = CA[i >>> 6 & 63];
	                dArr[d++] = CA[i & 63];
	                if (lineSep) {
	                    ++cc;
	                    if (cc == 19 && d < dLen - 2) {
	                        dArr[d++] = '\r';
	                        dArr[d++] = '\n';
	                        cc = 0;
	                    }
	                }
	            }

	            left = sLen - eLen;
	            if (left > 0) {
	                d = (sArr[eLen] & 255) << 10 | (left == 2 ? (sArr[sLen - 1] & 255) << 2 : 0);
	                dArr[dLen - 4] = CA[d >> 12];
	                dArr[dLen - 3] = CA[d >>> 6 & 63];
	                dArr[dLen - 2] = left == 2 ? CA[d & 63] : 61;
	                dArr[dLen - 1] = '=';
	            }

	            return dArr;
	        }
	    }

	    public static final byte[] decode(char[] sArr) {
	        int sLen = sArr != null ? sArr.length : 0;
	        if (sLen == 0) {
	            return new byte[0];
	        } else {
	            int sepCnt = 0;

	            int pad;
	            for(pad = 0; pad < sLen; ++pad) {
	                if (IA[sArr[pad]] < 0) {
	                    ++sepCnt;
	                }
	            }

	            if ((sLen - sepCnt) % 4 != 0) {
	                return null;
	            } else {
	                pad = 0;
	                int len = sLen;

	                while(len > 1) {
	                    --len;
	                    if (IA[sArr[len]] > 0) {
	                        break;
	                    }

	                    if (sArr[len] == '=') {
	                        ++pad;
	                    }
	                }

	                len = ((sLen - sepCnt) * 6 >> 3) - pad;
	                byte[] dArr = new byte[len];
	                int s = 0;
	                int d = 0;

	                while(d < len) {
	                    int i = 0;

	                    for(int j = 0; j < 4; ++j) {
	                        int c = IA[sArr[s++]];
	                        if (c >= 0) {
	                            i |= c << 18 - j * 6;
	                        } else {
	                            --j;
	                        }
	                    }

	                    dArr[d++] = (byte)(i >> 16);
	                    if (d < len) {
	                        dArr[d++] = (byte)(i >> 8);
	                        if (d < len) {
	                            dArr[d++] = (byte)i;
	                        }
	                    }
	                }

	                return dArr;
	            }
	        }
	    }

	    public static final byte[] encodeToByte(byte[] sArr, boolean lineSep) {
	        int sLen = sArr != null ? sArr.length : 0;
	        if (sLen == 0) {
	            return new byte[0];
	        } else {
	            int eLen = sLen / 3 * 3;
	            int cCnt = (sLen - 1) / 3 + 1 << 2;
	            int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0);
	            byte[] dArr = new byte[dLen];
	            int left = 0;
	            int d = 0;
	            int cc = 0;

	            while(left < eLen) {
	                int i = (sArr[left++] & 255) << 16 | (sArr[left++] & 255) << 8 | sArr[left++] & 255;
	                dArr[d++] = (byte)CA[i >>> 18 & 63];
	                dArr[d++] = (byte)CA[i >>> 12 & 63];
	                dArr[d++] = (byte)CA[i >>> 6 & 63];
	                dArr[d++] = (byte)CA[i & 63];
	                if (lineSep) {
	                    ++cc;
	                    if (cc == 19 && d < dLen - 2) {
	                        dArr[d++] = 13;
	                        dArr[d++] = 10;
	                        cc = 0;
	                    }
	                }
	            }

	            left = sLen - eLen;
	            if (left > 0) {
	                d = (sArr[eLen] & 255) << 10 | (left == 2 ? (sArr[sLen - 1] & 255) << 2 : 0);
	                dArr[dLen - 4] = (byte)CA[d >> 12];
	                dArr[dLen - 3] = (byte)CA[d >>> 6 & 63];
	                dArr[dLen - 2] = left == 2 ? (byte)CA[d & 63] : 61;
	                dArr[dLen - 1] = 61;
	            }

	            return dArr;
	        }
	    }

	    public static final byte[] decode(byte[] sArr) {
	        int sLen = sArr.length;
	        int sepCnt = 0;

	        int pad;
	        for(pad = 0; pad < sLen; ++pad) {
	            if (IA[sArr[pad] & 255] < 0) {
	                ++sepCnt;
	            }
	        }

	        if ((sLen - sepCnt) % 4 != 0) {
	            return null;
	        } else {
	            pad = 0;
	            int len = sLen;

	            while(len > 1) {
	                --len;
	                if (IA[sArr[len] & 255] > 0) {
	                    break;
	                }

	                if (sArr[len] == 61) {
	                    ++pad;
	                }
	            }

	            len = ((sLen - sepCnt) * 6 >> 3) - pad;
	            byte[] dArr = new byte[len];
	            int s = 0;
	            int d = 0;

	            while(d < len) {
	                int i = 0;

	                for(int j = 0; j < 4; ++j) {
	                    int c = IA[sArr[s++] & 255];
	                    if (c >= 0) {
	                        i |= c << 18 - j * 6;
	                    } else {
	                        --j;
	                    }
	                }

	                dArr[d++] = (byte)(i >> 16);
	                if (d < len) {
	                    dArr[d++] = (byte)(i >> 8);
	                    if (d < len) {
	                        dArr[d++] = (byte)i;
	                    }
	                }
	            }

	            return dArr;
	        }
	    }


	    public static final String encodeToString(byte[] sArr, boolean lineSep) {
	        return new String(encodeToChar(sArr, lineSep));
	    }

	    public static final byte[] decode(String str) {
	        int sLen = str != null ? str.length() : 0;
	        if (sLen == 0) {
	            return new byte[0];
	        } else {
	            int sepCnt = 0;

	            int pad;
	            for(pad = 0; pad < sLen; ++pad) {
	                if (IA[str.charAt(pad)] < 0) {
	                    ++sepCnt;
	                }
	            }

	            if ((sLen - sepCnt) % 4 != 0) {
	                return null;
	            } else {
	                pad = 0;
	                int len = sLen;

	                while(len > 1) {
	                    --len;
	                    if (IA[str.charAt(len)] > 0) {
	                        break;
	                    }

	                    if (str.charAt(len) == '=') {
	                        ++pad;
	                    }
	                }

	                len = ((sLen - sepCnt) * 6 >> 3) - pad;
	                byte[] dArr = new byte[len];
	                int s = 0;
	                int d = 0;

	                while(d < len) {
	                    int i = 0;

	                    for(int j = 0; j < 4; ++j) {
	                        int c = IA[str.charAt(s++)];
	                        if (c >= 0) {
	                            i |= c << 18 - j * 6;
	                        } else {
	                            --j;
	                        }
	                    }

	                    dArr[d++] = (byte)(i >> 16);
	                    if (d < len) {
	                        dArr[d++] = (byte)(i >> 8);
	                        if (d < len) {
	                            dArr[d++] = (byte)i;
	                        }
	                    }
	                }

	                return dArr;
	            }
	        }
	    }

	    public static final boolean isBase64Value(String str) {
	        int sLen = str != null ? str.length() : 0;
	        if (sLen == 0) {
	            return false;
	        } else {
	            int sepCnt = 0;

	            for(int i = 0; i < sLen; ++i) {
	                char currentChar = str.charAt(i);
	                if (currentChar >= IA.length) {
	                    return false;
	                }

	                if (IA[currentChar] < 0) {
	                    ++sepCnt;
	                }
	            }

	            if ((sLen - sepCnt) % 4 != 0) {
	                return false;
	            } else {
	                return true;
	            }
	        }
	    }


	    static {
	        Arrays.fill(IA, -1);
	        int i = 0;

	        for(int iS = CA.length; i < iS; IA[CA[i]] = i++) {
	        }

	        IA[61] = 0;
	    }

}
