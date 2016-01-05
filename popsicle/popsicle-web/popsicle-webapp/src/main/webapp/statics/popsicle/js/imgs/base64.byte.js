function Base64_byte() {
	this.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	this.base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62,
			-1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
			-1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1,
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
			43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
	this.head = [];
	this.code = [];
	this.init = function(head) {
		this.code = [];
		for (var index = 0; index < this.alphabet.length; index++) {
			this.code[this.alphabet.charCodeAt(index)] = index;
		}
		this.head = head;
	}

	this.decode = function(str) {
		str = str.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		var stringLength = str.length;
		while (str.charAt(stringLength - 1) === "=") {
			stringLength--;
		}
		var result = new Uint8Array((stringLength / 4 * 3) << 0);
		for (var stringOffset = 0, byteOffset = 0; stringOffset < stringLength; stringOffset += 4, byteOffset += 3) {
			stringOffset + 1 < stringLength
					&& (result[byteOffset + 0] = ((this.code[str
							.charCodeAt(stringOffset + 0)] & 0x3F) << 2)
							| ((this.code[str.charCodeAt(stringOffset + 1)] & 0x30) >> 4));
			stringOffset + 2 < stringLength
					&& (result[byteOffset + 1] = ((this.code[str
							.charCodeAt(stringOffset + 1)] & 0x0F) << 4)
							| ((this.code[str.charCodeAt(stringOffset + 2)] & 0x3C) >> 2));
			stringOffset + 3 < stringLength
					&& (result[byteOffset + 2] = ((this.code[str
							.charCodeAt(stringOffset + 2)] & 0x03) << 6)
							| ((this.code[str.charCodeAt(stringOffset + 3)] & 0x3F) << 0));
		}
		return result;
	}

	this.encode = function(arr) {
		var byteLength = arr.length;
		var result = "";
		for (var byteOffset = 0; byteOffset < byteLength; byteOffset += 3) {
			result += this.alphabet.charAt((arr[byteOffset] & 0xFC) >> 2);
			result += this.alphabet
					.charAt(((arr[byteOffset] & 0x03) << 4)
							| (byteOffset + 1 < byteLength ? (arr[byteOffset + 1] & 0xF0) >> 4
									: 0x00));
			result += byteOffset + 1 < byteLength ? this.alphabet
					.charAt(((arr[byteOffset + 1] & 0x0F) << 2)
							| (byteOffset + 2 < byteLength ? (arr[byteOffset + 2] & 0xC0) >> 6
									: 0x00))
					: "=";
			result += byteOffset + 2 < byteLength ? this.alphabet
					.charAt((arr[byteOffset + 2] & 0x3F) << 0) : "=";
		}
		return result;
	}

	/**input base64 String*/
	this.base64_decode = function(str) {
		var c1, c2, c3, c4;
		var i = 0, len = str.length, attBytes = [];
		while (i < len) {
			do {
				c1 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff]
			} while (i < len && c1 == -1);
			if (c1 == -1)
				break;
			do {
				c2 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff]
			} while (i < len && c2 == -1);
			if (c2 == -1)
				break;
			// string += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
			attBytes.push((c1 << 2) | ((c2 & 0x30) >> 4));
			do {
				c3 = str.charCodeAt(i++) & 0xff;
				if (c3 == 61)
					//return string;
					return attBytes;
				c3 = this.base64DecodeChars[c3]
			} while (i < len && c3 == -1);
			if (c3 == -1)
				break;
			// string += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >>
			// 2));
			attBytes.push(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
			do {
				c4 = str.charCodeAt(i++) & 0xff;
				if (c4 == 61)
					return attBytes;
				c4 = this.base64DecodeChars[c4]
			} while (i < len && c4 == -1);
			if (c4 == -1)
				break;
			// string += String.fromCharCode(((c3 & 0x03) << 6) | c4)
			attBytes.push(((c3 & 0x03) << 6) | c4);
		}
		// return string;
		return attBytes;
	}
}
